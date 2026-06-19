# ==============================================================================
# coverage-report.ps1
# Lee el CSV de JaCoCo y genera un dashboard HTML moderno y legible.
# Uso: .\scripts\coverage-report.ps1
# Salida: target\site\jacoco\coverage-dashboard.html
# ==============================================================================

param(
    [string]$CsvPath    = "target\site\jacoco\jacoco.csv",
    [string]$OutputPath = "target\site\jacoco\coverage-dashboard.html",
    [string]$ProjectName = "pago-service"
)

if (-not (Test-Path $CsvPath)) {
    Write-Error "No se encontro el archivo CSV de JaCoCo en: $CsvPath"
    Write-Host "Asegurate de correr 'mvn test' primero."
    exit 1
}

$csv = Import-Csv $CsvPath
$generatedAt = Get-Date -Format "dd/MM/yyyy HH:mm:ss"

# --- Helpers ---
function Get-Pct($covered, $missed) {
    $c = [int]$covered; $m = [int]$missed; $total = $c + $m
    if ($total -eq 0) { return -1 }
    return [math]::Round(($c / $total) * 100, 1)
}

function Get-Color($pct) {
    if ($pct -lt 0)   { return "#64748b" }  # sin datos
    if ($pct -ge 80)  { return "#22c55e" }  # verde
    if ($pct -ge 50)  { return "#f59e0b" }  # amarillo
    return "#ef4444"                         # rojo
}

function Get-Badge($pct) {
    if ($pct -lt 0)   { return "NO DATA" }
    if ($pct -ge 80)  { return "BUENA" }
    if ($pct -ge 50)  { return "MEDIA" }
    return "BAJA"
}

function Get-BadgeClass($pct) {
    if ($pct -lt 0)   { return "badge-none" }
    if ($pct -ge 80)  { return "badge-good" }
    if ($pct -ge 50)  { return "badge-mid" }
    return "badge-low"
}

function Build-Bar($pct, $color) {
    $w = if ($pct -lt 0) { 0 } else { $pct }
    $display = if ($pct -lt 0) { "n/a" } else { "$pct%" }
    return @"
        <div class="bar-wrap">
            <div class="bar-track">
                <div class="bar-fill" style="width:${w}%;background:$color;"></div>
            </div>
            <span class="bar-label" style="color:$color;">$display</span>
        </div>
"@
}

# --- Totales globales ---
$tIM = ($csv | Measure-Object -Property INSTRUCTION_MISSED   -Sum).Sum
$tIC = ($csv | Measure-Object -Property INSTRUCTION_COVERED  -Sum).Sum
$tBM = ($csv | Measure-Object -Property BRANCH_MISSED        -Sum).Sum
$tBC = ($csv | Measure-Object -Property BRANCH_COVERED       -Sum).Sum
$tLM = ($csv | Measure-Object -Property LINE_MISSED          -Sum).Sum
$tLC = ($csv | Measure-Object -Property LINE_COVERED         -Sum).Sum
$tMM = ($csv | Measure-Object -Property METHOD_MISSED        -Sum).Sum
$tMC = ($csv | Measure-Object -Property METHOD_COVERED       -Sum).Sum

$gInst   = Get-Pct $tIC $tIM
$gBranch = Get-Pct $tBC $tBM
$gLine   = Get-Pct $tLC $tLM
$gMethod = Get-Pct $tMC $tMM

$gInstColor   = Get-Color $gInst
$gBranchColor = Get-Color $gBranch
$gLineColor   = Get-Color $gLine
$gMethodColor = Get-Color $gMethod

$gInstDisplay   = if ($gInst   -lt 0) { "n/a" } else { "$gInst%"   }
$gBranchDisplay = if ($gBranch -lt 0) { "n/a" } else { "$gBranch%" }
$gLineDisplay   = if ($gLine   -lt 0) { "n/a" } else { "$gLine%"   }
$gMethodDisplay = if ($gMethod -lt 0) { "n/a" } else { "$gMethod%" }

# --- Agrupar por paquete ---
$packages = $csv | Group-Object -Property PACKAGE | Sort-Object Name

$packageSections = ""
foreach ($pkg in $packages) {
    $pkgName = $pkg.Name -replace "cl/videojuego/pago_service", "pago_service" -replace "/", "."
    if ($pkgName -eq "") { $pkgName = "(root)" }

    $pkgIM = ($pkg.Group | Measure-Object -Property INSTRUCTION_MISSED  -Sum).Sum
    $pkgIC = ($pkg.Group | Measure-Object -Property INSTRUCTION_COVERED -Sum).Sum
    $pkgPct = Get-Pct $pkgIC $pkgIM
    $pkgColor = Get-Color $pkgPct
    $pkgBadge = Get-Badge $pkgPct
    $pkgBadgeClass = Get-BadgeClass $pkgPct
    $pkgDisplay = if ($pkgPct -lt 0) { "n/a" } else { "$pkgPct%" }

    $classRows = ""
    foreach ($row in $pkg.Group | Sort-Object CLASS) {
        $cls   = $row.CLASS
        $iPct  = Get-Pct $row.INSTRUCTION_COVERED $row.INSTRUCTION_MISSED
        $bPct  = Get-Pct $row.BRANCH_COVERED      $row.BRANCH_MISSED
        $lPct  = Get-Pct $row.LINE_COVERED        $row.LINE_MISSED
        $mPct  = Get-Pct $row.METHOD_COVERED      $row.METHOD_MISSED

        $iBar = Build-Bar $iPct (Get-Color $iPct)
        $bBar = Build-Bar $bPct (Get-Color $bPct)
        $lBar = Build-Bar $lPct (Get-Color $lPct)
        $mBar = Build-Bar $mPct (Get-Color $mPct)

        $rowClass = if ($iPct -ge 80) { "row-good" } elseif ($iPct -ge 50) { "row-mid" } elseif ($iPct -lt 0) { "" } else { "row-low" }

        $classRows += @"
            <tr class="$rowClass">
                <td class="cls-name">$cls</td>
                <td>$iBar</td>
                <td>$bBar</td>
                <td>$lBar</td>
                <td>$mBar</td>
            </tr>
"@
    }

    $packageSections += @"
    <div class="pkg-card">
        <div class="pkg-header" onclick="togglePkg(this)">
            <span class="pkg-toggle">&#9660;</span>
            <span class="pkg-title">$pkgName</span>
            <span class="pkg-cov" style="color:$pkgColor;">$pkgDisplay</span>
            <span class="badge $pkgBadgeClass">$pkgBadge</span>
        </div>
        <div class="pkg-body">
            <table class="cls-table">
                <thead>
                    <tr>
                        <th>Clase</th>
                        <th>Instrucciones</th>
                        <th>Ramas</th>
                        <th>Lineas</th>
                        <th>Metodos</th>
                    </tr>
                </thead>
                <tbody>
                    $classRows
                </tbody>
            </table>
        </div>
    </div>
"@
}

# --- HTML final ---
$html = @"
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Coverage Report — $ProjectName</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        :root {
            --bg:        #0f172a;
            --surface:   #1e293b;
            --border:    #334155;
            --text:      #e2e8f0;
            --muted:     #94a3b8;
            --green:     #22c55e;
            --yellow:    #f59e0b;
            --red:       #ef4444;
            --accent:    #6366f1;
        }

        body { font-family: 'Inter', sans-serif; background: var(--bg); color: var(--text); min-height: 100vh; }

        /* ---------- HEADER ---------- */
        .header {
            background: linear-gradient(135deg, #1e1b4b 0%, #312e81 50%, #1e293b 100%);
            border-bottom: 1px solid var(--border);
            padding: 2.5rem 2rem 2rem;
        }
        .header-inner { max-width: 1100px; margin: 0 auto; }
        .header h1 { font-size: 1.8rem; font-weight: 700; color: #fff; letter-spacing: -0.5px; }
        .header h1 span { color: #a5b4fc; }
        .header-meta { font-size: 0.8rem; color: var(--muted); margin-top: 0.4rem; }

        /* ---------- MAIN ---------- */
        .main { max-width: 1100px; margin: 2rem auto; padding: 0 1.5rem 4rem; }

        /* ---------- METRIC CARDS ---------- */
        .cards { display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 1rem; margin-bottom: 2.5rem; }
        .card {
            background: var(--surface);
            border: 1px solid var(--border);
            border-radius: 12px;
            padding: 1.5rem;
            position: relative;
            overflow: hidden;
        }
        .card::before {
            content: '';
            position: absolute;
            top: 0; left: 0; right: 0;
            height: 3px;
            background: var(--accent-color, var(--accent));
            border-radius: 12px 12px 0 0;
        }
        .card-label { font-size: 0.75rem; font-weight: 600; color: var(--muted); text-transform: uppercase; letter-spacing: 0.08em; }
        .card-value { font-size: 3rem; font-weight: 700; line-height: 1; margin: 0.5rem 0 0.25rem; }
        .card-sub { font-size: 0.78rem; color: var(--muted); }

        /* radial circle */
        .card-circle { position: absolute; top: 1.2rem; right: 1.2rem; }
        .circle-svg { width: 56px; height: 56px; transform: rotate(-90deg); }
        .circle-track { fill: none; stroke: var(--border); stroke-width: 5; }
        .circle-fill  { fill: none; stroke-width: 5; stroke-linecap: round; transition: stroke-dasharray 1s ease; }

        /* ---------- PACKAGE CARDS ---------- */
        .section-title { font-size: 1.1rem; font-weight: 700; color: var(--text); margin-bottom: 1rem; padding-bottom: 0.5rem; border-bottom: 1px solid var(--border); }
        .pkg-card { background: var(--surface); border: 1px solid var(--border); border-radius: 10px; margin-bottom: 1rem; overflow: hidden; }
        .pkg-header {
            display: flex; align-items: center; gap: 0.75rem;
            padding: 1rem 1.25rem;
            cursor: pointer;
            user-select: none;
            transition: background 0.15s;
        }
        .pkg-header:hover { background: #263349; }
        .pkg-toggle { color: var(--muted); font-size: 0.75rem; transition: transform 0.2s; }
        .pkg-toggle.closed { transform: rotate(-90deg); }
        .pkg-title { flex: 1; font-size: 0.9rem; font-weight: 600; font-family: monospace; color: #c4b5fd; }
        .pkg-cov { font-size: 1.1rem; font-weight: 700; }
        .badge { font-size: 0.65rem; font-weight: 700; padding: 0.2rem 0.5rem; border-radius: 99px; text-transform: uppercase; letter-spacing: 0.05em; }
        .badge-good { background: #14532d; color: var(--green); }
        .badge-mid  { background: #451a03; color: var(--yellow); }
        .badge-low  { background: #450a0a; color: var(--red); }
        .badge-none { background: var(--border); color: var(--muted); }

        .pkg-body { padding: 0 1.25rem 1.25rem; }
        .pkg-body.hidden { display: none; }

        /* ---------- CLASS TABLE ---------- */
        .cls-table { width: 100%; border-collapse: collapse; font-size: 0.82rem; }
        .cls-table thead tr { background: #0f172a; }
        .cls-table th { padding: 0.6rem 0.75rem; text-align: left; font-weight: 600; font-size: 0.7rem; color: var(--muted); text-transform: uppercase; letter-spacing: 0.06em; border-bottom: 1px solid var(--border); }
        .cls-table td { padding: 0.55rem 0.75rem; border-bottom: 1px solid #1e2d3e; vertical-align: middle; }
        .cls-table tr:last-child td { border-bottom: none; }
        .cls-name { font-family: monospace; font-size: 0.82rem; color: #7dd3fc; font-weight: 500; }
        .row-good { background: rgba(34,197,94,0.04); }
        .row-mid  { background: rgba(245,158,11,0.04); }
        .row-low  { background: rgba(239,68,68,0.04); }

        /* ---------- BAR ---------- */
        .bar-wrap  { display: flex; align-items: center; gap: 0.5rem; }
        .bar-track { flex: 1; height: 6px; background: var(--border); border-radius: 99px; overflow: hidden; }
        .bar-fill  { height: 100%; border-radius: 99px; transition: width 0.8s ease; }
        .bar-label { min-width: 38px; text-align: right; font-weight: 600; font-size: 0.78rem; }

        /* ---------- LEGEND ---------- */
        .legend { display: flex; gap: 1.5rem; margin-bottom: 1.5rem; flex-wrap: wrap; }
        .legend-item { display: flex; align-items: center; gap: 0.4rem; font-size: 0.78rem; color: var(--muted); }
        .legend-dot { width: 10px; height: 10px; border-radius: 50%; }
        .dot-green  { background: var(--green); }
        .dot-yellow { background: var(--yellow); }
        .dot-red    { background: var(--red); }

        /* ---------- FOOTER ---------- */
        .footer { text-align: center; padding: 2rem; font-size: 0.75rem; color: var(--muted); border-top: 1px solid var(--border); }
    </style>
</head>
<body>

<div class="header">
    <div class="header-inner">
        <h1>&#128202; Coverage Report &mdash; <span>$ProjectName</span></h1>
        <div class="header-meta">Generado el $generatedAt &nbsp;&bull;&nbsp; JaCoCo 0.8.12 &nbsp;&bull;&nbsp; Java 21</div>
    </div>
</div>

<div class="main">

    <!-- Metric Cards -->
    <div class="cards">
        <div class="card" style="--accent-color: $gInstColor;">
            <div class="card-label">Instrucciones</div>
            <div class="card-value" style="color:$gInstColor;">$gInstDisplay</div>
            <div class="card-sub">$tIC cubiertas de $([int]$tIC + [int]$tIM) totales</div>
            <div class="card-circle">
                <svg class="circle-svg" viewBox="0 0 36 36">
                    <circle class="circle-track" cx="18" cy="18" r="15.9"/>
                    <circle class="circle-fill" cx="18" cy="18" r="15.9" stroke="$gInstColor"
                        stroke-dasharray="$(if ($gInst -ge 0) { "$gInst 100" } else { '0 100' })"/>
                </svg>
            </div>
        </div>
        <div class="card" style="--accent-color: $gBranchColor;">
            <div class="card-label">Ramas (Branches)</div>
            <div class="card-value" style="color:$gBranchColor;">$gBranchDisplay</div>
            <div class="card-sub">$tBC cubiertas de $([int]$tBC + [int]$tBM) totales</div>
            <div class="card-circle">
                <svg class="circle-svg" viewBox="0 0 36 36">
                    <circle class="circle-track" cx="18" cy="18" r="15.9"/>
                    <circle class="circle-fill" cx="18" cy="18" r="15.9" stroke="$gBranchColor"
                        stroke-dasharray="$(if ($gBranch -ge 0) { "$gBranch 100" } else { '0 100' })"/>
                </svg>
            </div>
        </div>
        <div class="card" style="--accent-color: $gLineColor;">
            <div class="card-label">Lineas</div>
            <div class="card-value" style="color:$gLineColor;">$gLineDisplay</div>
            <div class="card-sub">$tLC cubiertas de $([int]$tLC + [int]$tLM) totales</div>
            <div class="card-circle">
                <svg class="circle-svg" viewBox="0 0 36 36">
                    <circle class="circle-track" cx="18" cy="18" r="15.9"/>
                    <circle class="circle-fill" cx="18" cy="18" r="15.9" stroke="$gLineColor"
                        stroke-dasharray="$(if ($gLine -ge 0) { "$gLine 100" } else { '0 100' })"/>
                </svg>
            </div>
        </div>
        <div class="card" style="--accent-color: $gMethodColor;">
            <div class="card-label">Metodos</div>
            <div class="card-value" style="color:$gMethodColor;">$gMethodDisplay</div>
            <div class="card-sub">$tMC cubiertos de $([int]$tMC + [int]$tMM) totales</div>
            <div class="card-circle">
                <svg class="circle-svg" viewBox="0 0 36 36">
                    <circle class="circle-track" cx="18" cy="18" r="15.9"/>
                    <circle class="circle-fill" cx="18" cy="18" r="15.9" stroke="$gMethodColor"
                        stroke-dasharray="$(if ($gMethod -ge 0) { "$gMethod 100" } else { '0 100' })"/>
                </svg>
            </div>
        </div>
    </div>

    <!-- Legend -->
    <div class="legend">
        <span class="legend-item"><span class="legend-dot dot-green"></span> &ge;80% Buena cobertura</span>
        <span class="legend-item"><span class="legend-dot dot-yellow"></span> 50-79% Cobertura media</span>
        <span class="legend-item"><span class="legend-dot dot-red"></span> &lt;50% Cobertura baja</span>
    </div>

    <!-- Package Sections -->
    <div class="section-title">&#128230; Desglose por paquete</div>
    $packageSections

</div>

<div class="footer">
    Generado por <strong>coverage-report.ps1</strong> &bull; Powered by JaCoCo &bull; $ProjectName
</div>

<script>
    function togglePkg(header) {
        const body = header.nextElementSibling;
        const arrow = header.querySelector('.pkg-toggle');
        body.classList.toggle('hidden');
        arrow.classList.toggle('closed');
    }
</script>
</body>
</html>
"@

$html | Out-File -FilePath $OutputPath -Encoding UTF8
Write-Host ""
Write-Host "============================================="
Write-Host " Coverage Dashboard generado exitosamente!"
Write-Host " Archivo: $OutputPath"
Write-Host "============================================="
Write-Host ""
Write-Host " Instrucciones : $gInstDisplay"
Write-Host " Ramas         : $gBranchDisplay"
Write-Host " Lineas        : $gLineDisplay"
Write-Host " Metodos       : $gMethodDisplay"
Write-Host ""

# Abrir en navegador automaticamente
Start-Process $OutputPath
