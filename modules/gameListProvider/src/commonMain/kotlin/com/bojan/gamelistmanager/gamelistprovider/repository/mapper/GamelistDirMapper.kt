package com.bojan.gamelistmanager.gamelistprovider.repository.mapper

import com.bojan.gamelistmanager.gamelistprovider.domain.data.RetroArchCoreInfo

/**
 * Converts directory name to Playlist name for this system.
 * @param dir System directory.
 * @return Instance of [RetroArchCoreInfo] containing suggested core and playlist name.
 */
fun gameListDirToDatabase(dir: String): RetroArchCoreInfo {
    return when (dir) {
        "3do" -> RetroArchCoreInfo("opera_libretro","The 3DO Company - 3DO")
        "64dd" -> RetroArchCoreInfo("parallel_n64_libretro", "Nintendo - Nintendo 64DD")
        "amiga1200" -> RetroArchCoreInfo("puae_libretro", "Commodore - Amiga")
        "amiga600" -> RetroArchCoreInfo("puae_libretro", "Commodore - Amiga")
        "amigacd32" -> RetroArchCoreInfo("fsuae_libretro", "Commodore - Amiga")
        "amigacdtv" -> RetroArchCoreInfo("fsuae_libretro", "Commodore - Amiga")
        "amstradcpc" -> RetroArchCoreInfo( "cap32_libretro", "Amstrad - CPC")
        "apple2" -> RetroArchCoreInfo.empty
        "apple2gs" -> RetroArchCoreInfo.empty
        "atari2600" -> RetroArchCoreInfo( "stella_libretro", "Atari - 2600")
        "atari5200" -> RetroArchCoreInfo( "a5200_libretro", "Atari - 5200")
        "atari7800" -> RetroArchCoreInfo( "prosystem_libretro", "Atari - 7800")
        "atari800" -> RetroArchCoreInfo( "atari800_libretro", "Atari - 8-bit")
        "atarist" -> RetroArchCoreInfo( "hatari_libretro","Atari - ST")
        "bbcmicro" -> RetroArchCoreInfo.empty
        "bk" -> RetroArchCoreInfo( "bk_libretro", "BK-0011")
        "c64" -> RetroArchCoreInfo( "vice_x64_libretro", "Commodore - 64")
        "channelf" -> RetroArchCoreInfo( "freechaf_libretro", "Fairchild - Channel F")
        "colecovision" -> RetroArchCoreInfo( "bluemsx_libretro", "Coleco - ColecoVision")
        "daphne" -> RetroArchCoreInfo( "daphne_libretro", "")
        "dos" -> RetroArchCoreInfo( "dosbox_core_libretro", "DOS")
        "dreamcast" -> RetroArchCoreInfo( "flycast_libretro", "Sega - Dreamcast")
        "easyrpg" -> RetroArchCoreInfo( "easyrpg_libretro", "RPG Maker")
        "fbneo" -> RetroArchCoreInfo( "fbneo_libretro", "FBNeo - Arcade Games")
        "fds" -> RetroArchCoreInfo( "nestopia_libretro", "Nintendo - Family Computer Disk System")
        "gamegear" -> RetroArchCoreInfo( "picodrive_libretro", "Sega - Game Gear")
        "gb" -> RetroArchCoreInfo( "mgba_libretro", "Nintendo - Game Boy")
        "gba" -> RetroArchCoreInfo( "mgba_libretro", "Nintendo - Game Boy Advance")
        "gbc" -> RetroArchCoreInfo( "mgba_libretro", "Nintendo - Game Boy Color")
        "gw" -> RetroArchCoreInfo( "gw_libretro", "Handheld Electronic Game")
        "gx4000" -> RetroArchCoreInfo( "cap32_libretro", "Amstrad - GX4000")
        "intellivision" -> RetroArchCoreInfo( "freeintv_libretro", "Mattel - Intellivision")
        "lowresnx" -> RetroArchCoreInfo( "lowresnx_libretro", "LowRes NX")
        "lutro" -> RetroArchCoreInfo( "lutro_libretro", "Lutro")
        "lynx" -> RetroArchCoreInfo( "handy_libretro", "Atari - Lynx")
        "mame" -> RetroArchCoreInfo( "mame2003_plus_libretro", "MAME")
        "mastersystem" -> RetroArchCoreInfo( "picodrive_libretro", "Sega - Master System - Mark III")
        "megadrive" -> RetroArchCoreInfo( "picodrive_libretro", "Sega - Mega Drive - Genesis")
        "moonlight" -> RetroArchCoreInfo( "moonlight_libretro", "")
        "msx1" -> RetroArchCoreInfo( "bluemsx_libretro", "Microsoft - MSX")
        "msx2" -> RetroArchCoreInfo( "bluemsx_libretro", "Microsoft - MSX2")
        "msxturbor" -> RetroArchCoreInfo( "bluemsx_libretro", "")
        "multivision" -> RetroArchCoreInfo( "gearsystem_libretro", "")
        "n64" -> RetroArchCoreInfo( "parallel_n64_libretro", "Nintendo - Nintendo 64")
        "naomi" -> RetroArchCoreInfo( "flycast_libretro", "Sega - Naomi")
        "naomigd" -> RetroArchCoreInfo( "flycast_libretro", "")
        "neogeo" -> RetroArchCoreInfo( "fbneo_libretro", "SNK - Neo Geo")
        "neogeocd" -> RetroArchCoreInfo( "neocd_libretro", "SNK - Neo Geo CD")
        "nes" -> RetroArchCoreInfo( "nestopia_libretro", "Nintendo - Nintendo Entertainment System")
        "ngp" -> RetroArchCoreInfo( "mednafen_ngp_libretro", "SNK - Neo Geo Pocket")
        "ngpc" -> RetroArchCoreInfo( "mednafen_ngp_libretro", "SNK - Neo Geo Pocket Color")
        "o2em" -> RetroArchCoreInfo( "o2em_libretro", "Magnavox - Odyssey2")
        "openbor" -> RetroArchCoreInfo.empty
        "oricatmos" -> RetroArchCoreInfo.empty
        "palm" -> RetroArchCoreInfo( "mu_libretro", "")
        "pc88" -> RetroArchCoreInfo( "quasi88_libretro", "NEC - PC-8001 - PC-8801")
        "pc98" -> RetroArchCoreInfo( "np2kai_libretro", "NEC - PC-98")
        "pcengine" -> RetroArchCoreInfo( "mednafen_pce_libretro", "NEC - PC Engine - TurboGrafx 16")
        "pcenginecd" -> RetroArchCoreInfo( "mednafen_pce_libretro", "NEC - PC Engine CD - TurboGrafx-CD")
        "pcv2" -> RetroArchCoreInfo( "mednafen_wswan_libretro", "")
        "pico8" -> RetroArchCoreInfo( "retro8_libretro", "PICO8")
        "pokemini" -> RetroArchCoreInfo( "pokemini_libretro", "Nintendo - Pokemon Mini")
        "ports" -> RetroArchCoreInfo.empty
        "psp" -> RetroArchCoreInfo( "ppsspp_libretro", "Sony - PlayStation Portable")
        "psx" -> RetroArchCoreInfo( "pcsx_rearmed_libretro", "Sony - PlayStation")
        "ps2" -> RetroArchCoreInfo( "play_libretro", "Sony - PlayStation 2")
        "samcoupe" -> RetroArchCoreInfo( "simcp_libretro", "SAM coupe")
        "satellaview" -> RetroArchCoreInfo( "snes9x_libretro", "Nintendo - Satellaview")
        "scummvm" -> RetroArchCoreInfo( "scummvm_libretro", "ScummVM")
        "scv" -> RetroArchCoreInfo( "emuscv_libretro", "Epoch - Super Cassette Vision")
        "sega32x" -> RetroArchCoreInfo( "picodrive_libretro", "Sega - 32X")
        "segacd" -> RetroArchCoreInfo( "picodrive_libretro", "Sega - Mega-CD - Sega CD")
        "sg1000" -> RetroArchCoreInfo( "genesis_plus_gx_libretro", "Sega - SG-1000")
        "snes" -> RetroArchCoreInfo( "snes9x_libretro", "Nintendo - Super Nintendo Entertainment System")
        "solarus" -> RetroArchCoreInfo.empty
        "spectravideo" -> RetroArchCoreInfo( "bluemsx_libretro", "Spectravideo - SVI-318 - SVI-328")
        "sufami" -> RetroArchCoreInfo( "snes9x_libretro", "Nintendo - Sufami Turbo")
        "supergrafx" -> RetroArchCoreInfo( "mednafen_supergrafx_libretro", "NEC - PC Engine SuperGrafx")
        "supervision" -> RetroArchCoreInfo( "potator_libretro", "Watara - Supervision")
        "thomson" -> RetroArchCoreInfo( "theodore_libretro", "Thomson - MOTO")
        "ti994a" -> RetroArchCoreInfo.empty
        "tic80" -> RetroArchCoreInfo( "tic80_libretro", "TIC-80")
        "trs80coco" -> RetroArchCoreInfo.empty
        "uzebox" -> RetroArchCoreInfo( "uzem_libretro", "Uzebox")
        "vectrex" -> RetroArchCoreInfo( "vecx_libretro", "GCE - Vectrex")
        "vic20" -> RetroArchCoreInfo( "vice_xvic_libretro", "Commodore - VIC-20")
        "videopacplus" -> RetroArchCoreInfo( "o2em_libretro", "Philips - Videopac+")
        "virtualboy" -> RetroArchCoreInfo( "mednafen_vb_libretro", "Nintendo - Virtual Boy")
        "wswan" -> RetroArchCoreInfo( "mednafen_wswan_libretro", "Bandai - WonderSwan")
        "wswanc" -> RetroArchCoreInfo( "mednafen_wswan_libretro", "Bandai - WonderSwan Color")
        "x1" -> RetroArchCoreInfo( "x1_libretro", "Sharp - X1")
        "x68000" -> RetroArchCoreInfo( "px68k_libretro", "Sharp - X68000")
        "zx81" -> RetroArchCoreInfo( "81_libretro", "Sinclair - ZX 81")
        "zxspectrum" -> RetroArchCoreInfo( "fuse_libretro", "Sinclair - ZX Spectrum")
        else -> RetroArchCoreInfo.empty
    }
}

/**
 * Converts directory to proper System name.
 * @param dir System directory.
 * @return System name.
 */
fun gameListDirToSystemName(dir: String): String {
    return when (dir) {
        "3do" -> "The 3DO Interactive Multiplayer"
        "64dd" -> "Nintendo 64 Disk Drive"
        "amiga1200" -> "Commodore Amiga 1200"
        "amiga600" -> "Commodore Amiga 600"
        "amigacd32" -> "Commodore Amiga CD 32"
        "amigacdtv" -> "Commodore Amiga CD TV"
        "amstradcpc" -> "Amstrad CPC"
        "apple2" -> "Apple II"
        "apple2gs" -> "Apple II GS"
        "atari2600" -> "Atari 2600"
        "atari5200" -> "Atari 5200"
         "atari7800" -> "Atari 7800"
        "atari800" -> "Atari 8-bit"
        "atarist" -> "Atari ST"
        "bbcmicro" -> "Acorn BBC Micro"
        "bk" -> "Elektronika BK"
        "c64" -> "Commodore 64"
        "channelf" -> "Fairchild Channel F"
        "colecovision" -> "ColecoVision"
        "daphne" -> "Daphne (Arcade system)"
        "dos" -> "DOS"
        "dreamcast" -> "Sega Dreamcast"
        "dragon" -> "Dragon 32/64"
        "easyrpg" -> "RPG Maker"
        "fbneo" -> "FBNeo - Arcade Games"
        "fds" -> "Nintendo - Family Computer Disk System"
        "gamegear" -> "Sega - Game Gear"
        "gb" -> "Nintendo Game Boy"
        "gba" -> "Nintendo Game Boy Advance"
        "gbc" -> "Nintendo Game Boy Color"
        "gw" -> "Handheld Electronic Game"
        "gx4000" -> "Amstrad GX4000"
        "intellivision" -> "Mattel Intellivision"
        "lowresnx" -> "LowRes NX"
        "lutro" -> "Lutro"
        "lynx" -> "Atari - Lynx"
        "mame" -> "MAME"
        "mastersystem" -> "Sega Master System"
        "megadrive" -> "Sega Mega Drive - Genesis"
        "moonlight" -> "Moonlight "
        "msx1" -> "Microsoft MSX"
        "msx2" -> "Microsoft MSX2"
        "msxturbor" -> "MSXturboR"
        "multivision" -> "Othello Multivision"
        "n64" -> "Nintendo 64"
        "naomi" -> "Sega Naomi"
        "naomigd" -> "Sega NAOMI GD-ROM System"
        "neogeo" -> "SNK Neo Geo"
        "neogeocd" -> "SNK Neo Geo CD"
        "nes" -> "Nintendo Entertainment System (NES) - Famicom"
        "ngp" -> "SNK Neo Geo Pocket"
        "ngpc" -> "SNK Neo Geo Pocket Color"
        "o2em" -> "Magnavox Odyssey2"
        "openbor" -> "OpenBOR"
        "oricatmos" -> "Oric/Atmos"
        "palm" -> "Palm"
        "pc88" -> "NEC PC-8001 - PC-8801"
        "pc98" -> "NEC PC-98"
        "pcengine" -> "PC Engine - TurboGrafx 16"
        "pcenginecd" -> "PC Engine CD - TurboGrafx-CD"
        "pcv2" -> "Pocket Challenge v2"
        "pico8" -> "PICO-8"
        "pokemini" -> "Nintendo Pokemon Mini"
        "ports" -> "Ports"
        "psp" -> "Sony PlayStation Portable"
        "psx" -> "Sony PlayStation"
        "ps2" -> "Sony PlayStation 2"
        "samcoupe" -> "SAM coupe"
        "satellaview" -> "Nintendo Satellaview"
        "scummvm" -> "ScummVM"
        "scv" -> "Epoch Super Cassette Vision"
        "sega32x" -> "Sega 32X"
        "segacd" -> "Sega Mega-CD - Sega CD"
        "sg1000" -> "Sega SG-1000"
        "snes" -> "Super Nintendo Entertainment System - Super Famicom"
        "solarus" -> "Solarus"
        "spectravideo" -> "Spectravideo SVI-318 - SVI-328"
        "sufami" -> "Sufami Turbo"
        "supergrafx" -> "PC Engine SuperGrafx"
        "supervision" -> "Watara Supervision"
        "thomson" -> "Thomson MOTO"
        "ti994a" -> "TI-99/4A"
        "tic80" -> "TIC-80"
        "trs80coco" -> "TRS-80 Color Computer"
        "uzebox" -> "Uzebox"
        "vectrex" -> "GCE Vectrex"
        "vic20" -> "Commodore VIC-20"
        "videopacplus" -> "Philips Videopac+"
        "virtualboy" -> "Nintendo Virtual Boy"
        "wswan" -> "Bandai WonderSwan"
        "wswanc" -> "Bandai WonderSwan Color"
        "x1" -> "Sharp X1"
        "x68000" -> "Sharp X68000"
        "zx81" -> "ZX 81"
        "zxspectrum" -> "ZX Spectrum"
        else -> {
            "Unknown - $dir"
        }
    }
}