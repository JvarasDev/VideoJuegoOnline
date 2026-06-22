<#
  build-with-settings.ps1
  Uso: desde PowerShell en Windows.
  -Copia un `settings.xml` (plantilla rellenada) dentro del directorio del servicio,
   opcionalmente copia un certificado `corp-proxy.crt`, ejecuta `docker build --network=host`,
   y limpia los archivos temporales.

  Ejemplo:
    .\build-with-settings.ps1 -ServicePath .\api-gateway -SettingsPath ..\Dev\docker_helpers\settings.xml.template -CertPath ..\corp-proxy.crt -ImageTag api-gateway:local
#>
param(
  [Parameter(Mandatory=$true)] [string] $ServicePath,
  [Parameter(Mandatory=$false)] [string] $SettingsPath,
  [Parameter(Mandatory=$false)] [string] $CertPath,
  [Parameter(Mandatory=$true)] [string] $ImageTag
)

# Resolve provided paths to absolute paths BEFORE changing directory
$settingsFull = $null
$certFull = $null
if ($SettingsPath) {
  try { $settingsFull = (Resolve-Path -Path $SettingsPath -ErrorAction Stop).ProviderPath }
  catch { Write-Warning "settings not found at '$SettingsPath' - skipping copy." }
}
if ($CertPath) {
  try { $certFull = (Resolve-Path -Path $CertPath -ErrorAction Stop).ProviderPath }
  catch { Write-Warning "cert not found at '$CertPath' - skipping copy." }
}

Push-Location $ServicePath
try {
  if ($settingsFull) {
    Copy-Item -Path $settingsFull -Destination .\settings.xml -Force -ErrorAction Stop
    if (Test-Path .\settings.xml) { Write-Host "settings.xml copiado a $ServicePath" }
    else { Write-Warning "Falló copiar settings.xml" }
  }
  if ($certFull) {
    Copy-Item -Path $certFull -Destination .\corp-proxy.crt -Force -ErrorAction Stop
    if (Test-Path .\corp-proxy.crt) { Write-Host "corp-proxy.crt copiado a $ServicePath" }
    else { Write-Warning "Falló copiar corp-proxy.crt" }
  }

  Write-Host "Iniciando build Docker para $ServicePath con red host (ayuda a problemas de proxy/SSL)"
  docker build --network=host -t $ImageTag .
}
finally {
  if (Test-Path .\settings.xml) { Remove-Item -Path .\settings.xml -Force -ErrorAction SilentlyContinue }
  if (Test-Path .\corp-proxy.crt) { Remove-Item -Path .\corp-proxy.crt -Force -ErrorAction SilentlyContinue }
  Pop-Location
}
