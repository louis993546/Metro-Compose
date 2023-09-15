# Debugging on WSL2

WSL2 doesn't yet natively support USB devices but there's an open source project
which adds support https://learn.microsoft.com/en-us/windows/wsl/connect-usb .

After attatching your device with `usbipd wsl attach --busid=<BUSID>` using a
PowerShell instance with admin privileges it'll be available in WSL2, both directly
and containerized using the `.devcontainer`.
