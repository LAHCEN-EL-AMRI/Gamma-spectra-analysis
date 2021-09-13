; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Gamma spectra analysis"
#define MyAppVersion "1.0"
#define MyAppPublisher "Lahcen El Amri"
#define MyAppExeName "GSA.exe"

[Setup]
; NOTE: The value of AppId uniquely identifies this application. Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{CBBCA9CF-A849-48BA-BEAA-761750D8BF00}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
DefaultDirName={autopf}\{#MyAppName}
DefaultGroupName={#MyAppName}
LicenseFile=C:\Users\LAHCEN\Desktop\LICENSE.txt
; Uncomment the following line to run in non administrative install mode (install for current user only.)
;PrivilegesRequired=lowest
OutputDir=C:\Users\LAHCEN\Desktop\GSA\Install\Setup
OutputBaseFilename=Setup
SetupIconFile=C:\Users\LAHCEN\Desktop\GSA\Install\icons\image.ico
Compression=lzma
SolidCompression=yes
WizardStyle=modern

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "C:\Users\LAHCEN\Desktop\GSA\Install\GSA.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\LAHCEN\Desktop\GSA\Install\Doc\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\LAHCEN\Desktop\GSA\Install\example\*"; DestDir: "{app}\example\"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\LAHCEN\Desktop\GSA\Install\icons\*"; DestDir: "{app}\icons\"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\LAHCEN\Desktop\GSA\Install\Lib\*"; DestDir: "{app}\Lib\"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\LAHCEN\Desktop\GSA\Install\results\*"; DestDir: "{app}\results\"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"
Name: "{autodesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent

