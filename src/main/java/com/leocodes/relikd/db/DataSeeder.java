package com.leocodes.relikd.db;

import com.leocodes.relikd.dao.ComputerDAO;
import com.leocodes.relikd.model.Computer;
import com.leocodes.relikd.model.Condition;
import com.leocodes.relikd.model.Era;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class DataSeeder {

    private final ComputerDAO computerDAO = new ComputerDAO();

    public void seedIfEmpty() {
        try {
            if (!computerDAO.findAll().isEmpty()) {
                return;
            }
            seedPioneers();
            seedHomeComputer();
            seedRiseOfGui();
            seedCloneWars();
            seedMultimediaBoom();
            seedDotCom();
            seedLateVintage();
            seedAppleIAndII();
            seedCompactMac();
            seedMacIIAndQuadra();
            seedPowerPCMac();
            seedIMacAndG();
        } catch (SQLException e) {
            throw new RuntimeException("Database seeding failed", e);
        }
    }

    private void seedPioneers() throws SQLException {
        save("MITS", "Altair 8800", 1975, Era.PIONEERS, Condition.FAIR, 11500.00, 1,
                "The machine that started the personal computer revolution. Sold as a kit in Popular Electronics magazine.",
                "{\n" +
                "  \"cpu\": \"Intel 8080\",\n" +
                "  \"ram\": \"256 bytes\",\n" +
                "  \"storage\": \"None (toggle switches)\",\n" +
                "  \"display\": \"Front panel LEDs\"\n" +
                "}");

        save("IMSAI", "8080", 1975, Era.PIONEERS, Condition.GOOD, 7250.00, 1,
                "The first clone of the Altair 8800 and one of the most iconic S-100 bus computers. Featured in WarGames (1983).",
                "{\n" +
                "  \"cpu\": \"Intel 8080A\",\n" +
                "  \"ram\": \"4 KB\",\n" +
                "  \"storage\": \"8-inch floppy (optional)\",\n" +
                "  \"display\": \"Front panel LEDs\"\n" +
                "}");

        save("Processor Technology", "SOL-20", 1976, Era.PIONEERS, Condition.FAIR, 5750.00, 1,
                "One of the first fully assembled personal computers with a built-in keyboard and video output.",
                "{\n" +
                "  \"cpu\": \"Intel 8080\",\n" +
                "  \"ram\": \"1 KB\",\n" +
                "  \"storage\": \"Cassette tape\",\n" +
                "  \"display\": \"Composite video\"\n" +
                "}");

        save("Commodore", "PET 2001", 1977, Era.PIONEERS, Condition.GOOD, 3350.00, 2,
                "Commodore's first personal computer. All-in-one design with built-in monitor and cassette drive.",
                "{\n" +
                "  \"cpu\": \"MOS 6502 @ 1 MHz\",\n" +
                "  \"ram\": \"4 KB\",\n" +
                "  \"storage\": \"Built-in cassette\",\n" +
                "  \"display\": \"9-inch monochrome CRT\"\n" +
                "}");

        save("Tandy", "TRS-80 Model I", 1977, Era.PIONEERS, Condition.EXCELLENT, 1300.00, 2,
                "One of the 1977 Trinity alongside Apple II and PET. Sold through RadioShack stores nationwide.",
                "{\n" +
                "  \"cpu\": \"Zilog Z80 @ 1.77 MHz\",\n" +
                "  \"ram\": \"4 KB\",\n" +
                "  \"storage\": \"Cassette tape\",\n" +
                "  \"display\": \"12-inch monochrome monitor\"\n" +
                "}");

        save("Heathkit", "H8", 1977, Era.PIONEERS, Condition.PARTS, 2750.00, 1,
                "A popular kit computer sold through Heathkit's legendary catalog. Required manual assembly and soldering.",
                "{\n" +
                "  \"cpu\": \"Intel 8080A\",\n" +
                "  \"ram\": \"4 KB\",\n" +
                "  \"storage\": \"Paper tape / cassette\",\n" +
                "  \"display\": \"Octal front panel\"\n" +
                "}");
    }

    private void seedHomeComputer() throws SQLException {
        save("Commodore", "VIC-20", 1981, Era.HOME_COMPUTER, Condition.GOOD, 225.00, 3,
                "The first computer to sell one million units. Affordable and accessible to the masses.",
                "{\n" +
                "  \"cpu\": \"MOS 6502 @ 1 MHz\",\n" +
                "  \"ram\": \"5 KB\",\n" +
                "  \"storage\": \"Cassette / cartridge\",\n" +
                "  \"display\": \"176x184, 16 colors\"\n" +
                "}");

        save("Commodore", "64", 1982, Era.HOME_COMPUTER, Condition.EXCELLENT, 350.00, 4,
                "The best-selling single personal computer model of all time with ~17 million units sold.",
                "{\n" +
                "  \"cpu\": \"MOS 6510 @ 1.023 MHz\",\n" +
                "  \"ram\": \"64 KB\",\n" +
                "  \"storage\": \"1541 floppy drive\",\n" +
                "  \"display\": \"320x200, 16 colors\",\n" +
                "  \"sound\": \"SID 6581 (3 voices)\"\n" +
                "}");

        save("Sinclair", "ZX Spectrum 48K", 1982, Era.HOME_COMPUTER, Condition.GOOD, 290.00, 2,
                "The machine that launched the British gaming industry. Iconic rubber keyboard design.",
                "{\n" +
                "  \"cpu\": \"Zilog Z80A @ 3.5 MHz\",\n" +
                "  \"ram\": \"48 KB\",\n" +
                "  \"storage\": \"Cassette tape\",\n" +
                "  \"display\": \"256x192, 8 colors\"\n" +
                "}");

        save("Atari", "800XL", 1983, Era.HOME_COMPUTER, Condition.EXCELLENT, 325.00, 3,
                "The most popular of the Atari 8-bit line. Excellent build quality and custom chipset for its time.",
                "{\n" +
                "  \"cpu\": \"MOS 6502C @ 1.79 MHz\",\n" +
                "  \"ram\": \"64 KB\",\n" +
                "  \"storage\": \"410 cassette / 1050 floppy\",\n" +
                "  \"display\": \"320x192, 256 colors\",\n" +
                "  \"sound\": \"POKEY (4 voices)\"\n" +
                "}");

        save("Texas Instruments", "TI-99/4A", 1981, Era.HOME_COMPUTER, Condition.GOOD, 150.00, 2,
                "The first home computer with a 16-bit processor. Featured TI's Speech Synthesizer peripheral.",
                "{\n" +
                "  \"cpu\": \"TMS9900 @ 3 MHz\",\n" +
                "  \"ram\": \"16 KB\",\n" +
                "  \"storage\": \"Cassette tape\",\n" +
                "  \"display\": \"256x192, 16 colors\"\n" +
                "}");

        save("Acorn", "BBC Micro Model B", 1981, Era.HOME_COMPUTER, Condition.FAIR, 675.00, 1,
                "Designed for the BBC's Computer Literacy Project. Foundational to UK computing education.",
                "{\n" +
                "  \"cpu\": \"MOS 6502A @ 2 MHz\",\n" +
                "  \"ram\": \"32 KB\",\n" +
                "  \"storage\": \"Cassette / floppy\",\n" +
                "  \"display\": \"640x256, 8 colors\"\n" +
                "}");

        save("Amstrad", "CPC 464", 1984, Era.HOME_COMPUTER, Condition.GOOD, 280.00, 2,
                "Amstrad's all-in-one computer with built-in cassette deck and bundled monitor. Huge in Europe.",
                "{\n" +
                "  \"cpu\": \"Zilog Z80A @ 4 MHz\",\n" +
                "  \"ram\": \"64 KB\",\n" +
                "  \"storage\": \"Built-in cassette\",\n" +
                "  \"display\": \"320x200, 27 colors\"\n" +
                "}");

        save("MSX", "Philips VG-8020", 1983, Era.HOME_COMPUTER, Condition.FAIR, 250.00, 1,
                "Part of Microsoft's MSX standard — an attempt to create a unified home computer platform across manufacturers.",
                "{\n" +
                "  \"cpu\": \"Zilog Z80 @ 3.58 MHz\",\n" +
                "  \"ram\": \"64 KB\",\n" +
                "  \"storage\": \"Cassette tape\",\n" +
                "  \"display\": \"256x192, 16 colors\"\n" +
                "}");
    }

    private void seedRiseOfGui() throws SQLException {
        save("Commodore", "Amiga 1000", 1985, Era.RISE_OF_GUI, Condition.FAIR, 750.00, 1,
                "The original Amiga. Revolutionary custom chipset delivered multimedia capabilities years ahead of its time.",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 7.16 MHz\",\n" +
                "  \"ram\": \"256 KB\",\n" +
                "  \"storage\": \"880 KB floppy\",\n" +
                "  \"display\": \"640x400, 4096 colors\",\n" +
                "  \"chipset\": \"OCS (Agnus, Paula, Denise)\"\n" +
                "}");

        save("Atari", "520ST", 1985, Era.RISE_OF_GUI, Condition.GOOD, 550.00, 2,
                "Known as the 'Jackintosh' for its Mac-like GUI at a fraction of the price. Dominated MIDI music production.",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 8 MHz\",\n" +
                "  \"ram\": \"512 KB\",\n" +
                "  \"storage\": \"360 KB floppy\",\n" +
                "  \"display\": \"640x400 mono / 320x200 color\",\n" +
                "  \"os\": \"TOS / GEM\"\n" +
                "}");

        save("Commodore", "Amiga 500", 1987, Era.RISE_OF_GUI, Condition.EXCELLENT, 650.00, 3,
                "The best-selling Amiga model. Brought powerful multimedia computing to the consumer market.",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 7.16 MHz\",\n" +
                "  \"ram\": \"512 KB\",\n" +
                "  \"storage\": \"880 KB floppy\",\n" +
                "  \"display\": \"640x400, 4096 colors\",\n" +
                "  \"chipset\": \"ECS\"\n" +
                "}");

        save("NeXT", "Computer (Cube)", 1988, Era.RISE_OF_GUI, Condition.MINT, 17000.00, 1,
                "Steve Jobs' post-Apple masterpiece. The first web server ever ran on a NeXT Cube. NeXTSTEP became the basis for macOS.",
                "{\n" +
                "  \"cpu\": \"Motorola 68030 @ 25 MHz\",\n" +
                "  \"ram\": \"8 MB\",\n" +
                "  \"storage\": \"256 MB magneto-optical\",\n" +
                "  \"display\": \"MegaPixel 17-inch mono\",\n" +
                "  \"os\": \"NeXTSTEP\"\n" +
                "}");

        save("Acorn", "Archimedes A3000", 1989, Era.RISE_OF_GUI, Condition.GOOD, 850.00, 1,
                "Powered by the ARM processor — designed by Acorn. The ARM architecture now powers virtually every smartphone on Earth.",
                "{\n" +
                "  \"cpu\": \"ARM2 @ 8 MHz\",\n" +
                "  \"ram\": \"1 MB\",\n" +
                "  \"storage\": \"Floppy\",\n" +
                "  \"display\": \"640x512, 256 colors\",\n" +
                "  \"os\": \"RISC OS\"\n" +
                "}");

        save("Commodore", "Amiga 2000", 1987, Era.RISE_OF_GUI, Condition.GOOD, 1100.00, 1,
                "The professional Amiga with Zorro II expansion slots. Widely used in video production and broadcast.",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 7.16 MHz\",\n" +
                "  \"ram\": \"1 MB\",\n" +
                "  \"storage\": \"880 KB floppy\",\n" +
                "  \"display\": \"640x400, 4096 colors\",\n" +
                "  \"chipset\": \"ECS\"\n" +
                "}");
    }

    private void seedCloneWars() throws SQLException {
        save("IBM", "PC AT 5170", 1984, Era.CLONE_WARS, Condition.FAIR, 1200.00, 1,
                "The AT (Advanced Technology) set the standard for the modern PC. Introduced the 16-bit ISA bus.",
                "{\n" +
                "  \"cpu\": \"Intel 80286 @ 6 MHz\",\n" +
                "  \"ram\": \"256 KB\",\n" +
                "  \"storage\": \"20 MB HDD\",\n" +
                "  \"display\": \"EGA\"\n" +
                "}");

        save("Compaq", "Deskpro 386", 1986, Era.CLONE_WARS, Condition.GOOD, 1400.00, 1,
                "Beat IBM to market with the first 386 PC. A landmark moment that shifted power from IBM to the clone makers.",
                "{\n" +
                "  \"cpu\": \"Intel 80386 @ 16 MHz\",\n" +
                "  \"ram\": \"1 MB\",\n" +
                "  \"storage\": \"40 MB HDD\",\n" +
                "  \"display\": \"VGA\"\n" +
                "}");

        save("IBM", "PS/2 Model 80", 1987, Era.CLONE_WARS, Condition.FAIR, 800.00, 1,
                "IBM's attempt to reclaim the PC market with proprietary Micro Channel Architecture. Introduced the PS/2 port.",
                "{\n" +
                "  \"cpu\": \"Intel 80386 @ 20 MHz\",\n" +
                "  \"ram\": \"2 MB\",\n" +
                "  \"storage\": \"115 MB HDD\",\n" +
                "  \"display\": \"VGA\"\n" +
                "}");

        save("Dell", "316LT", 1989, Era.CLONE_WARS, Condition.PARTS, 250.00, 1,
                "One of Dell's earliest laptops. Helped establish Dell's direct-to-consumer business model.",
                "{\n" +
                "  \"cpu\": \"Intel 80386SX @ 16 MHz\",\n" +
                "  \"ram\": \"1 MB\",\n" +
                "  \"storage\": \"20 MB HDD\",\n" +
                "  \"display\": \"10-inch VGA LCD\"\n" +
                "}");

        save("IBM", "ThinkPad 700C", 1992, Era.CLONE_WARS, Condition.EXCELLENT, 1150.00, 1,
                "The original ThinkPad. Its iconic black design, red TrackPoint, and 10.4-inch TFT color screen set the standard for business laptops.",
                "{\n" +
                "  \"cpu\": \"Intel 486SLC2 @ 25 MHz\",\n" +
                "  \"ram\": \"4 MB\",\n" +
                "  \"storage\": \"120 MB HDD\",\n" +
                "  \"display\": \"10.4-inch TFT color\"\n" +
                "}");

        save("AT&T", "PC 6300", 1984, Era.CLONE_WARS, Condition.GOOD, 850.00, 2,
                "Actually manufactured by Olivetti (M24), this was AT&T's entry into the PC clone market. Featured a faster 8086 CPU than the original IBM PC.",
                "{\n" +
                "  \"cpu\": \"Intel 8086 @ 8 MHz\",\n" +
                "  \"ram\": \"256 KB\",\n" +
                "  \"storage\": \"20 MB HDD\",\n" +
                "  \"display\": \"High-res monochrome (640x400)\"\n" +
                "}");

        save("Gateway", "2000 4DX2-66V", 1993, Era.CLONE_WARS, Condition.GOOD, 675.00, 2,
                "Sold via Gateway's signature cow-spotted boxes. The 486DX2-66 was the power user's chip of the early 90s.",
                "{\n" +
                "  \"cpu\": \"Intel 486DX2 @ 66 MHz\",\n" +
                "  \"ram\": \"8 MB\",\n" +
                "  \"storage\": \"340 MB HDD\",\n" +
                "  \"display\": \"14-inch SVGA\"\n" +
                "}");

        save("Packard Bell", "Legend 300CD", 1993, Era.CLONE_WARS, Condition.FAIR, 220.00, 2,
                "The quintessential early-90s family PC. Found in millions of homes despite its questionable build quality.",
                "{\n" +
                "  \"cpu\": \"Intel 486SX @ 25 MHz\",\n" +
                "  \"ram\": \"4 MB\",\n" +
                "  \"storage\": \"170 MB HDD\",\n" +
                "  \"display\": \"14-inch VGA\"\n" +
                "}");
    }

    private void seedMultimediaBoom() throws SQLException {
        save("Gateway", "P5-75", 1995, Era.MULTIMEDIA_BOOM, Condition.GOOD, 450.00, 2,
                "A mid-range Pentium tower from Gateway's golden era. Shipped with Windows 95 and a cow-spotted box.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium @ 75 MHz\",\n" +
                "  \"ram\": \"8 MB\",\n" +
                "  \"storage\": \"540 MB HDD\",\n" +
                "  \"display\": \"15-inch SVGA\",\n" +
                "  \"optical\": \"2x CD-ROM\"\n" +
                "}");

        save("Dell", "Dimension XPS P133c", 1996, Era.MULTIMEDIA_BOOM, Condition.EXCELLENT, 550.00, 1,
                "Dell's enthusiast-tier Pentium desktop. The XPS line started here — performance PCs sold direct.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium @ 133 MHz\",\n" +
                "  \"ram\": \"16 MB\",\n" +
                "  \"storage\": \"1.6 GB HDD\",\n" +
                "  \"display\": \"15-inch monitor\",\n" +
                "  \"optical\": \"6x CD-ROM\"\n" +
                "}");

        save("IBM", "Aptiva S Series", 1995, Era.MULTIMEDIA_BOOM, Condition.GOOD, 400.00, 2,
                "IBM's consumer multimedia PC line. Distinctive charcoal design with integrated speakers.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium @ 100 MHz\",\n" +
                "  \"ram\": \"16 MB\",\n" +
                "  \"storage\": \"1.2 GB HDD\",\n" +
                "  \"display\": \"14-inch SVGA\",\n" +
                "  \"optical\": \"4x CD-ROM\",\n" +
                "  \"sound\": \"SoundBlaster 16\"\n" +
                "}");

        save("Compaq", "Presario 4860", 1997, Era.MULTIMEDIA_BOOM, Condition.FAIR, 250.00, 2,
                "Compaq's mainstream Pentium MMX multimedia desktop. Designed for families entering the internet age.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium MMX @ 200 MHz\",\n" +
                "  \"ram\": \"32 MB\",\n" +
                "  \"storage\": \"3.2 GB HDD\",\n" +
                "  \"optical\": \"8x CD-ROM\",\n" +
                "  \"modem\": \"33.6K modem\"\n" +
                "}");

        save("Sony", "VAIO PCV-90", 1997, Era.MULTIMEDIA_BOOM, Condition.EXCELLENT, 550.00, 1,
                "Sony's first VAIO desktop for the US market. Purple accent design made it instantly recognizable.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium MMX @ 200 MHz\",\n" +
                "  \"ram\": \"32 MB\",\n" +
                "  \"storage\": \"4 GB HDD\",\n" +
                "  \"display\": \"15-inch Trinitron\",\n" +
                "  \"optical\": \"8x CD-ROM\"\n" +
                "}");

        save("HP", "Pavilion 7270", 1998, Era.MULTIMEDIA_BOOM, Condition.GOOD, 220.00, 2,
                "HP's consumer Pavilion line brought branded PCs to mainstream retail. This model bridged the Pentium II era.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium II @ 266 MHz\",\n" +
                "  \"ram\": \"64 MB\",\n" +
                "  \"storage\": \"6.4 GB HDD\",\n" +
                "  \"optical\": \"24x CD-ROM\",\n" +
                "  \"modem\": \"56K V.90\"\n" +
                "}");
    }

    private void seedDotCom() throws SQLException {
        save("Dell", "Dimension 4100", 2000, Era.DOT_COM, Condition.GOOD, 120.00, 3,
                "Dell's workhorse desktop during the dot-com boom. Found in countless homes and small offices.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium III @ 800 MHz\",\n" +
                "  \"ram\": \"128 MB SDRAM\",\n" +
                "  \"storage\": \"20 GB HDD\",\n" +
                "  \"optical\": \"DVD-ROM\",\n" +
                "  \"os\": \"Windows ME\"\n" +
                "}");

        save("eMachines", "eTower 400i", 1999, Era.DOT_COM, Condition.FAIR, 140.00, 2,
                "The budget king of the late 90s. Sold at Walmart for under $400 and brought millions online for the first time.",
                "{\n" +
                "  \"cpu\": \"Intel Celeron @ 400 MHz\",\n" +
                "  \"ram\": \"32 MB SDRAM\",\n" +
                "  \"storage\": \"4.3 GB HDD\",\n" +
                "  \"optical\": \"CD-ROM\",\n" +
                "  \"modem\": \"56K\"\n" +
                "}");

        save("Compaq", "Presario 5000", 2000, Era.DOT_COM, Condition.GOOD, 110.00, 2,
                "Compaq's Y2K-era consumer desktop with the angular, silver-and-blue design language of the early 2000s.",
                "{\n" +
                "  \"cpu\": \"AMD Athlon @ 700 MHz\",\n" +
                "  \"ram\": \"128 MB SDRAM\",\n" +
                "  \"storage\": \"20 GB HDD\",\n" +
                "  \"optical\": \"CD-RW\",\n" +
                "  \"os\": \"Windows ME\"\n" +
                "}");

        save("Sony", "VAIO PCV-RX550", 2001, Era.DOT_COM, Condition.EXCELLENT, 450.00, 1,
                "Sony's premium tower with a distinctive translucent blue panel. Built for multimedia content creation.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium 4 @ 1.5 GHz\",\n" +
                "  \"ram\": \"256 MB RDRAM\",\n" +
                "  \"storage\": \"60 GB HDD\",\n" +
                "  \"optical\": \"DVD-RW\",\n" +
                "  \"os\": \"Windows XP\"\n" +
                "}");

        save("Gateway", "Essential 500SE", 2000, Era.DOT_COM, Condition.FAIR, 90.00, 2,
                "Gateway's budget line during the brief era when they were America's #2 PC maker.",
                "{\n" +
                "  \"cpu\": \"AMD Duron @ 700 MHz\",\n" +
                "  \"ram\": \"64 MB SDRAM\",\n" +
                "  \"storage\": \"10 GB HDD\",\n" +
                "  \"optical\": \"CD-ROM\",\n" +
                "  \"os\": \"Windows ME\"\n" +
                "}");

        save("HP", "Pavilion 513w", 2001, Era.DOT_COM, Condition.GOOD, 120.00, 2,
                "HP's early XP-era desktop tower. The Pavilion line dominated retail shelves at Best Buy and Circuit City.",
                "{\n" +
                "  \"cpu\": \"AMD Athlon XP 1600+\",\n" +
                "  \"ram\": \"256 MB DDR\",\n" +
                "  \"storage\": \"40 GB HDD\",\n" +
                "  \"optical\": \"CD-RW / DVD-ROM\",\n" +
                "  \"os\": \"Windows XP Home\"\n" +
                "}");
    }

    private void seedLateVintage() throws SQLException {
        save("Dell", "Dimension 8300", 2003, Era.LATE_VINTAGE, Condition.GOOD, 140.00, 2,
                "Dell's enthusiast-level Pentium 4 tower with Hyper-Threading. The last great era of the beige/silver mini-tower.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium 4 @ 3.0 GHz (HT)\",\n" +
                "  \"ram\": \"512 MB DDR\",\n" +
                "  \"storage\": \"120 GB HDD\",\n" +
                "  \"gpu\": \"ATI Radeon 9800 Pro\",\n" +
                "  \"os\": \"Windows XP Pro\"\n" +
                "}");

        save("Alienware", "Area-51 7500", 2004, Era.LATE_VINTAGE, Condition.EXCELLENT, 1300.00, 1,
                "A pre-Dell-acquisition Alienware tower. Hand-built in Miami with aggressive styling and serious gaming hardware.",
                "{\n" +
                "  \"cpu\": \"AMD Athlon 64 3500+\",\n" +
                "  \"ram\": \"1 GB DDR\",\n" +
                "  \"storage\": \"160 GB SATA HDD\",\n" +
                "  \"gpu\": \"NVIDIA GeForce 6800 Ultra\",\n" +
                "  \"os\": \"Windows XP\"\n" +
                "}");

        save("HP", "Pavilion a1000n", 2004, Era.LATE_VINTAGE, Condition.GOOD, 90.00, 3,
                "HP's mainstream desktop with AMD 64-bit. Came bundled with HP's media center software.",
                "{\n" +
                "  \"cpu\": \"AMD Athlon 64 3200+\",\n" +
                "  \"ram\": \"512 MB DDR\",\n" +
                "  \"storage\": \"160 GB HDD\",\n" +
                "  \"optical\": \"DVD+RW/+R\",\n" +
                "  \"os\": \"Windows XP Media Center\"\n" +
                "}");

        save("Dell", "XPS 400", 2005, Era.LATE_VINTAGE, Condition.EXCELLENT, 150.00, 1,
                "The successor to the Dimension XPS line. Bridged the gap between mainstream and enthusiast with BTX form factor.",
                "{\n" +
                "  \"cpu\": \"Intel Pentium D 920 @ 2.8 GHz\",\n" +
                "  \"ram\": \"1 GB DDR2\",\n" +
                "  \"storage\": \"250 GB SATA HDD\",\n" +
                "  \"gpu\": \"ATI Radeon X600 SE\",\n" +
                "  \"os\": \"Windows XP Media Center\"\n" +
                "}");

        save("Gateway", "GT5220", 2006, Era.LATE_VINTAGE, Condition.FAIR, 80.00, 2,
                "One of Gateway's last towers before being acquired by Acer. Featured AMD's competitive Athlon 64 X2 dual-core.",
                "{\n" +
                "  \"cpu\": \"AMD Athlon 64 X2 3800+\",\n" +
                "  \"ram\": \"1 GB DDR2\",\n" +
                "  \"storage\": \"250 GB HDD\",\n" +
                "  \"gpu\": \"NVIDIA GeForce 6150\",\n" +
                "  \"os\": \"Windows XP Media Center\"\n" +
                "}");
    }

    private void seedAppleIAndII() throws SQLException {
        save("Apple", "I", 1976, Era.APPLE_I_AND_II, Condition.PARTS, 600000.00, 1,
                "Only ~200 were ever made, ~80 are known to survive. Hand-built by Steve Wozniak. The genesis of Apple Computer.",
                "{\n" +
                "  \"cpu\": \"MOS 6502 @ 1 MHz\",\n" +
                "  \"ram\": \"4 KB (expandable to 8 KB)\",\n" +
                "  \"storage\": \"Cassette interface\",\n" +
                "  \"display\": \"40x24 text via RF modulator\"\n" +
                "}");

        save("Apple", "II", 1977, Era.APPLE_I_AND_II, Condition.GOOD, 3500.00, 1,
                "The machine that made Apple. First mass-produced PC with color graphics. Defined the personal computer industry.",
                "{\n" +
                "  \"cpu\": \"MOS 6502 @ 1.023 MHz\",\n" +
                "  \"ram\": \"4-48 KB\",\n" +
                "  \"storage\": \"Cassette / Disk II floppy\",\n" +
                "  \"display\": \"280x192, 6 colors\",\n" +
                "  \"expansion\": \"8 slots\"\n" +
                "}");

        save("Apple", "II Plus", 1979, Era.APPLE_I_AND_II, Condition.EXCELLENT, 1500.00, 2,
                "Added Applesoft BASIC in ROM (written by Microsoft). The upgrade that made the Apple II platform even more accessible.",
                "{\n" +
                "  \"cpu\": \"MOS 6502 @ 1.023 MHz\",\n" +
                "  \"ram\": \"48 KB\",\n" +
                "  \"storage\": \"Disk II floppy\",\n" +
                "  \"display\": \"280x192, 6 colors\"\n" +
                "}");

        save("Apple", "III", 1980, Era.APPLE_I_AND_II, Condition.FAIR, 1200.00, 1,
                "Apple's ill-fated business computer. Notoriously unreliable due to Steve Jobs insisting on no fan — chips would unseat from overheating.",
                "{\n" +
                "  \"cpu\": \"Synertek 6502A @ 2 MHz\",\n" +
                "  \"ram\": \"128 KB\",\n" +
                "  \"storage\": \"5.25-inch floppy\",\n" +
                "  \"display\": \"560x192, 16 colors\",\n" +
                "  \"os\": \"Apple SOS\"\n" +
                "}");

        save("Apple", "Lisa", 1983, Era.APPLE_I_AND_II, Condition.MINT, 35000.00, 1,
                "The first personal computer with a GUI sold commercially. Named after Steve Jobs' daughter. A technical marvel that was too expensive to succeed ($9,995).",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 5 MHz\",\n" +
                "  \"ram\": \"1 MB\",\n" +
                "  \"storage\": \"Two 860 KB Twiggy drives\",\n" +
                "  \"display\": \"720x364 mono\",\n" +
                "  \"os\": \"Lisa OS\"\n" +
                "}");

        save("Apple", "IIe", 1983, Era.APPLE_I_AND_II, Condition.EXCELLENT, 650.00, 3,
                "The 'Enhanced' Apple II. The longest-lived Apple II model — produced for 11 years. The backbone of school computer labs.",
                "{\n" +
                "  \"cpu\": \"MOS 6502 @ 1.023 MHz\",\n" +
                "  \"ram\": \"64 KB\",\n" +
                "  \"storage\": \"Disk II floppy\",\n" +
                "  \"display\": \"280x192, 16 colors\",\n" +
                "  \"expansion\": \"8 slots\"\n" +
                "}");

        save("Apple", "IIc", 1984, Era.APPLE_I_AND_II, Condition.GOOD, 500.00, 2,
                "The first compact Apple II. Portable design with built-in floppy drive. Apple's first attempt at a portable computer.",
                "{\n" +
                "  \"cpu\": \"65C02 @ 1.023 MHz\",\n" +
                "  \"ram\": \"128 KB\",\n" +
                "  \"storage\": \"Built-in 5.25-inch floppy\",\n" +
                "  \"display\": \"280x192, 16 colors\"\n" +
                "}");
    }

    private void seedCompactMac() throws SQLException {
        save("Apple", "Macintosh 128K", 1984, Era.COMPACT_MAC, Condition.FAIR, 1500.00, 1,
                "The original Macintosh. '1984' Super Bowl ad. 'Hello.' Changed everything. The first mass-market GUI computer.",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 7.83 MHz\",\n" +
                "  \"ram\": \"128 KB\",\n" +
                "  \"storage\": \"400 KB floppy\",\n" +
                "  \"display\": \"9-inch 512x342 mono\",\n" +
                "  \"os\": \"System 1.0\"\n" +
                "}");

        save("Apple", "Macintosh 512K", 1984, Era.COMPACT_MAC, Condition.GOOD, 1200.00, 1,
                "Nicknamed 'Fat Mac' — quadrupled the RAM of the original. Made the Mac actually usable for real work.",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 7.83 MHz\",\n" +
                "  \"ram\": \"512 KB\",\n" +
                "  \"storage\": \"400 KB floppy\",\n" +
                "  \"display\": \"9-inch 512x342 mono\",\n" +
                "  \"os\": \"System 2.0\"\n" +
                "}");

        save("Apple", "Macintosh Plus", 1986, Era.COMPACT_MAC, Condition.EXCELLENT, 850.00, 2,
                "Added SCSI port and 1 MB RAM. Produced for over 4 years — the longest production run of any Mac.",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 7.83 MHz\",\n" +
                "  \"ram\": \"1 MB (expandable to 4 MB)\",\n" +
                "  \"storage\": \"800 KB floppy + SCSI\",\n" +
                "  \"display\": \"9-inch 512x342 mono\",\n" +
                "  \"os\": \"System 3.0+\"\n" +
                "}");

        save("Apple", "Macintosh SE", 1987, Era.COMPACT_MAC, Condition.GOOD, 450.00, 2,
                "First compact Mac with an expansion slot (SE = System Expansion). Also the first Mac with an internal hard drive option.",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 7.83 MHz\",\n" +
                "  \"ram\": \"1 MB (expandable to 4 MB)\",\n" +
                "  \"storage\": \"20 MB HDD / 800 KB floppy\",\n" +
                "  \"display\": \"9-inch 512x342 mono\",\n" +
                "  \"expansion\": \"1 PDS slot\"\n" +
                "}");

        save("Apple", "Macintosh SE/30", 1989, Era.COMPACT_MAC, Condition.MINT, 1200.00, 1,
                "The greatest compact Mac ever made. 68030 processor in the classic form factor. Could run A/UX (Apple's UNIX). Beloved by collectors.",
                "{\n" +
                "  \"cpu\": \"Motorola 68030 @ 15.67 MHz\",\n" +
                "  \"ram\": \"1 MB (expandable to 128 MB)\",\n" +
                "  \"storage\": \"40 MB HDD\",\n" +
                "  \"display\": \"9-inch 512x342 mono\",\n" +
                "  \"expansion\": \"1 PDS slot\"\n" +
                "}");

        save("Apple", "Macintosh Classic", 1990, Era.COMPACT_MAC, Condition.GOOD, 400.00, 3,
                "The first Mac under $1,000. Brought the Mac to the mass market. Had a ROM disk with System 6.0.3 built in.",
                "{\n" +
                "  \"cpu\": \"Motorola 68000 @ 7.83 MHz\",\n" +
                "  \"ram\": \"1 MB (expandable to 4 MB)\",\n" +
                "  \"storage\": \"40 MB HDD\",\n" +
                "  \"display\": \"9-inch 512x342 mono\",\n" +
                "  \"os\": \"System 6.0.7\"\n" +
                "}");

        save("Apple", "Macintosh Classic II", 1991, Era.COMPACT_MAC, Condition.EXCELLENT, 350.00, 2,
                "The last true compact Mac. Upgraded to the 68030 processor. End of an iconic era in Apple design.",
                "{\n" +
                "  \"cpu\": \"Motorola 68030 @ 16 MHz\",\n" +
                "  \"ram\": \"2 MB (expandable to 10 MB)\",\n" +
                "  \"storage\": \"40 MB HDD\",\n" +
                "  \"display\": \"9-inch 512x342 mono\",\n" +
                "  \"os\": \"System 7.0\"\n" +
                "}");
    }

    private void seedMacIIAndQuadra() throws SQLException {
        save("Apple", "Macintosh II", 1987, Era.MAC_II_AND_QUADRA, Condition.FAIR, 1100.00, 1,
                "The first modular, expandable Mac. First Mac with color. Six NuBus expansion slots. A professional powerhouse.",
                "{\n" +
                "  \"cpu\": \"Motorola 68020 @ 15.67 MHz\",\n" +
                "  \"ram\": \"1 MB (expandable to 68 MB)\",\n" +
                "  \"storage\": \"40 MB HDD\",\n" +
                "  \"display\": \"Color via NuBus card\",\n" +
                "  \"expansion\": \"6 NuBus slots\"\n" +
                "}");

        save("Apple", "Macintosh IIcx", 1989, Era.MAC_II_AND_QUADRA, Condition.GOOD, 550.00, 1,
                "Compact version of the Mac II. Three NuBus slots in a smaller, quieter case. Popular in offices.",
                "{\n" +
                "  \"cpu\": \"Motorola 68030 @ 15.67 MHz\",\n" +
                "  \"ram\": \"1 MB (expandable to 128 MB)\",\n" +
                "  \"storage\": \"40 MB HDD\",\n" +
                "  \"expansion\": \"3 NuBus slots\"\n" +
                "}");

        save("Apple", "Macintosh IIci", 1989, Era.MAC_II_AND_QUADRA, Condition.EXCELLENT, 650.00, 2,
                "The most popular Mac II. First Mac with built-in video. Praised for its architecture — remained in production for 4 years.",
                "{\n" +
                "  \"cpu\": \"Motorola 68030 @ 25 MHz\",\n" +
                "  \"ram\": \"1 MB (expandable to 128 MB)\",\n" +
                "  \"storage\": \"40 MB HDD\",\n" +
                "  \"display\": \"Built-in video support\",\n" +
                "  \"expansion\": \"3 NuBus slots\"\n" +
                "}");

        save("Apple", "Macintosh IIfx", 1990, Era.MAC_II_AND_QUADRA, Condition.MINT, 1800.00, 1,
                "The 'Wicked Fast' Mac. Apple's fastest and most expensive Mac at the time ($9,870). Used dedicated I/O processors.",
                "{\n" +
                "  \"cpu\": \"Motorola 68030 @ 40 MHz\",\n" +
                "  \"ram\": \"4 MB (expandable to 128 MB)\",\n" +
                "  \"storage\": \"80 MB HDD\",\n" +
                "  \"expansion\": \"6 NuBus slots\"\n" +
                "}");

        save("Apple", "Quadra 700", 1991, Era.MAC_II_AND_QUADRA, Condition.GOOD, 600.00, 2,
                "First Mac with Ethernet built in. Tower form factor. Aimed squarely at the workstation market.",
                "{\n" +
                "  \"cpu\": \"Motorola 68040 @ 25 MHz\",\n" +
                "  \"ram\": \"4 MB (expandable to 68 MB)\",\n" +
                "  \"storage\": \"80 MB HDD\",\n" +
                "  \"expansion\": \"2 NuBus slots\",\n" +
                "  \"networking\": \"Built-in Ethernet\"\n" +
                "}");

        save("Apple", "Quadra 950", 1992, Era.MAC_II_AND_QUADRA, Condition.FAIR, 950.00, 1,
                "Apple's full-tower powerhouse. Five NuBus slots and massive expandability. The Mac server of its era.",
                "{\n" +
                "  \"cpu\": \"Motorola 68040 @ 33 MHz\",\n" +
                "  \"ram\": \"8 MB (expandable to 256 MB)\",\n" +
                "  \"storage\": \"200 MB HDD\",\n" +
                "  \"expansion\": \"5 NuBus slots\"\n" +
                "}");

        save("Apple", "Quadra 840AV", 1993, Era.MAC_II_AND_QUADRA, Condition.EXCELLENT, 1200.00, 1,
                "The 'AV' Macs featured DSP chips for real-time speech recognition and video capture — futuristic for 1993.",
                "{\n" +
                "  \"cpu\": \"Motorola 68040 @ 40 MHz\",\n" +
                "  \"ram\": \"8 MB (expandable to 128 MB)\",\n" +
                "  \"storage\": \"230 MB HDD\",\n" +
                "  \"special\": \"AT&T 3210 DSP for AV\",\n" +
                "  \"expansion\": \"3 NuBus slots\"\n" +
                "}");

        save("Apple", "LC 475", 1993, Era.MAC_II_AND_QUADRA, Condition.GOOD, 350.00, 2,
                "The compact 'pizza box' Mac. The LC line made Macs affordable for schools and homes.",
                "{\n" +
                "  \"cpu\": \"Motorola 68040 @ 25 MHz\",\n" +
                "  \"ram\": \"4 MB (expandable to 36 MB)\",\n" +
                "  \"storage\": \"80 MB HDD\",\n" +
                "  \"expansion\": \"1 PDS slot\"\n" +
                "}");
    }

    private void seedPowerPCMac() throws SQLException {
        save("Apple", "Power Macintosh 6100/60", 1994, Era.POWERPC_MAC, Condition.GOOD, 250.00, 2,
                "The first PowerPC Mac. Code-named 'PDM' (Piltdown Man). Began the transition from Motorola 68K to IBM/Motorola PowerPC.",
                "{\n" +
                "  \"cpu\": \"PowerPC 601 @ 60 MHz\",\n" +
                "  \"ram\": \"8 MB (expandable to 72 MB)\",\n" +
                "  \"storage\": \"160 MB HDD\",\n" +
                "  \"expansion\": \"1 PDS slot\",\n" +
                "  \"os\": \"System 7.1.2\"\n" +
                "}");

        save("Apple", "Power Macintosh 8100/80", 1994, Era.POWERPC_MAC, Condition.EXCELLENT, 450.00, 1,
                "The high-end launch PowerPC Mac. Full-size tower with three NuBus slots. Flagship of the new architecture.",
                "{\n" +
                "  \"cpu\": \"PowerPC 601 @ 80 MHz\",\n" +
                "  \"ram\": \"8 MB (expandable to 264 MB)\",\n" +
                "  \"storage\": \"250 MB HDD\",\n" +
                "  \"expansion\": \"3 NuBus slots\",\n" +
                "  \"os\": \"System 7.1.2\"\n" +
                "}");

        save("Apple", "Power Macintosh 9500/120", 1995, Era.POWERPC_MAC, Condition.GOOD, 550.00, 1,
                "Apple's first PCI-based Power Mac. Six PCI slots. Designed for video production professionals.",
                "{\n" +
                "  \"cpu\": \"PowerPC 604 @ 120 MHz\",\n" +
                "  \"ram\": \"16 MB (expandable to 1.5 GB)\",\n" +
                "  \"storage\": \"1 GB HDD\",\n" +
                "  \"expansion\": \"6 PCI slots\",\n" +
                "  \"os\": \"System 7.5.2\"\n" +
                "}");

        save("Apple", "PowerBook 5300", 1995, Era.POWERPC_MAC, Condition.FAIR, 400.00, 1,
                "Apple's first PowerPC laptop. Plagued by battery fires (literally) and quality issues. A rare collector's piece.",
                "{\n" +
                "  \"cpu\": \"PowerPC 603e @ 100 MHz\",\n" +
                "  \"ram\": \"8 MB (expandable to 64 MB)\",\n" +
                "  \"storage\": \"500 MB HDD\",\n" +
                "  \"display\": \"10.4-inch dual-scan color\"\n" +
                "}");

        save("Apple", "Twentieth Anniversary Macintosh", 1997, Era.POWERPC_MAC, Condition.MINT, 5000.00, 1,
                "Limited edition for Apple's 20th anniversary. Flat-panel LCD, Bose speakers, concierge delivery by a tuxedo-clad technician.",
                "{\n" +
                "  \"cpu\": \"PowerPC 603e @ 250 MHz\",\n" +
                "  \"ram\": \"32 MB\",\n" +
                "  \"storage\": \"2 GB HDD\",\n" +
                "  \"display\": \"12.1-inch active-matrix LCD\",\n" +
                "  \"sound\": \"Bose speaker system\"\n" +
                "}");

        save("Apple", "Power Macintosh G3 (Beige)", 1997, Era.POWERPC_MAC, Condition.GOOD, 300.00, 2,
                "The first G3 Mac. Available in minitower and desktop configurations. Marked the end of Apple's beige era.",
                "{\n" +
                "  \"cpu\": \"PowerPC G3 @ 233-300 MHz\",\n" +
                "  \"ram\": \"32 MB (expandable to 768 MB)\",\n" +
                "  \"storage\": \"4 GB HDD\",\n" +
                "  \"expansion\": \"3 PCI slots\",\n" +
                "  \"os\": \"Mac OS 8.0\"\n" +
                "}");
    }

    private void seedIMacAndG() throws SQLException {
        save("Apple", "iMac G3", 1998, Era.IMAC_AND_G, Condition.EXCELLENT, 350.00, 3,
                "The computer that saved Apple. Bondi Blue translucent design by Jony Ive. Killed the floppy drive. 'Hello (again).'",
                "{\n" +
                "  \"cpu\": \"PowerPC G3 @ 233 MHz\",\n" +
                "  \"ram\": \"32 MB\",\n" +
                "  \"storage\": \"4 GB HDD\",\n" +
                "  \"display\": \"15-inch CRT\",\n" +
                "  \"os\": \"Mac OS 8.1\",\n" +
                "  \"design\": \"Bondi Blue translucent\"\n" +
                "}");

        save("Apple", "Power Mac G4", 1999, Era.IMAC_AND_G, Condition.GOOD, 250.00, 2,
                "So powerful the US government classified it as a weapon. Introduced the graphite 'Yosemite' enclosure.",
                "{\n" +
                "  \"cpu\": \"PowerPC G4 @ 400-500 MHz\",\n" +
                "  \"ram\": \"64 MB (expandable to 1.5 GB)\",\n" +
                "  \"storage\": \"10 GB HDD\",\n" +
                "  \"expansion\": \"4 PCI/AGP slots\",\n" +
                "  \"os\": \"Mac OS 9\"\n" +
                "}");

        save("Apple", "iBook G3 (Clamshell)", 1999, Era.IMAC_AND_G, Condition.EXCELLENT, 450.00, 2,
                "The consumer laptop counterpart to the iMac. Tangerine and Blueberry colors. Iconic clamshell design with carry handle.",
                "{\n" +
                "  \"cpu\": \"PowerPC G3 @ 300 MHz\",\n" +
                "  \"ram\": \"32 MB\",\n" +
                "  \"storage\": \"3.2 GB HDD\",\n" +
                "  \"display\": \"12.1-inch TFT\",\n" +
                "  \"wireless\": \"Optional AirPort\",\n" +
                "  \"os\": \"Mac OS 8.6\"\n" +
                "}");

        save("Apple", "Power Mac G4 Cube", 2000, Era.IMAC_AND_G, Condition.MINT, 1500.00, 1,
                "An 8-inch suspended cube with no fan. A design icon that failed commercially but won a spot in MoMA's permanent collection.",
                "{\n" +
                "  \"cpu\": \"PowerPC G4 @ 450 MHz\",\n" +
                "  \"ram\": \"64 MB (expandable to 1.5 GB)\",\n" +
                "  \"storage\": \"20 GB HDD\",\n" +
                "  \"design\": \"8-inch acrylic cube\",\n" +
                "  \"os\": \"Mac OS 9.0.4\"\n" +
                "}");

        save("Apple", "PowerBook G4 (Titanium)", 2001, Era.IMAC_AND_G, Condition.GOOD, 400.00, 1,
                "The first titanium laptop. 15.2-inch widescreen. The coolest laptop ever made. Thin, powerful, and impossibly stylish.",
                "{\n" +
                "  \"cpu\": \"PowerPC G4 @ 400-500 MHz\",\n" +
                "  \"ram\": \"128 MB\",\n" +
                "  \"storage\": \"10 GB HDD\",\n" +
                "  \"display\": \"15.2-inch 1280x854\",\n" +
                "  \"os\": \"Mac OS X\"\n" +
                "}");

        save("Apple", "iMac G4", 2002, Era.IMAC_AND_G, Condition.EXCELLENT, 450.00, 2,
                "The 'Sunflower' or 'iLamp' iMac. Hemispherical base with a floating flat-panel display on a chrome arm. Designed by Jony Ive.",
                "{\n" +
                "  \"cpu\": \"PowerPC G4 @ 700 MHz - 1.25 GHz\",\n" +
                "  \"ram\": \"256 MB\",\n" +
                "  \"storage\": \"60 GB HDD\",\n" +
                "  \"display\": \"15/17/20-inch LCD on arm\",\n" +
                "  \"os\": \"Mac OS X 10.1+\"\n" +
                "}");

        save("Apple", "Power Mac G5", 2003, Era.IMAC_AND_G, Condition.GOOD, 350.00, 2,
                "The aluminum cheese-grater tower. First 64-bit desktop. Dual-processor configurations. Used for serious creative work.",
                "{\n" +
                "  \"cpu\": \"PowerPC G5 @ 1.6-2.5 GHz\",\n" +
                "  \"ram\": \"256 MB (expandable to 8 GB)\",\n" +
                "  \"storage\": \"80 GB HDD\",\n" +
                "  \"expansion\": \"3 PCI-X slots + AGP\",\n" +
                "  \"os\": \"Mac OS X 10.3 Panther\"\n" +
                "}");

        save("Apple", "iMac G5", 2004, Era.IMAC_AND_G, Condition.EXCELLENT, 200.00, 2,
                "All components behind the flat panel display — pioneered the all-in-one form factor that defines the iMac to this day.",
                "{\n" +
                "  \"cpu\": \"PowerPC G5 @ 1.6-2.1 GHz\",\n" +
                "  \"ram\": \"256 MB (expandable to 2 GB)\",\n" +
                "  \"storage\": \"80 GB HDD\",\n" +
                "  \"display\": \"17/20-inch widescreen LCD\",\n" +
                "  \"os\": \"Mac OS X 10.3 Panther\"\n" +
                "}");

        save("Apple", "PowerBook G4 (Aluminum)", 2003, Era.IMAC_AND_G, Condition.GOOD, 180.00, 2,
                "Replaced the Titanium with anodized aluminum. Available in 12, 15, and 17-inch. Backlit keyboard introduced on the 17-inch.",
                "{\n" +
                "  \"cpu\": \"PowerPC G4 @ 1.0-1.67 GHz\",\n" +
                "  \"ram\": \"256 MB\",\n" +
                "  \"storage\": \"60 GB HDD\",\n" +
                "  \"display\": \"12/15/17-inch\",\n" +
                "  \"os\": \"Mac OS X 10.3+\"\n" +
                "}");

        save("Apple", "Mac mini G4", 2005, Era.IMAC_AND_G, Condition.EXCELLENT, 150.00, 3,
                "BYODKM — Bring Your Own Display, Keyboard, and Mouse. The $499 entry point to the Mac. 6.5 x 6.5 x 2 inches.",
                "{\n" +
                "  \"cpu\": \"PowerPC G4 @ 1.25-1.42 GHz\",\n" +
                "  \"ram\": \"256 MB\",\n" +
                "  \"storage\": \"40 GB HDD\",\n" +
                "  \"display\": \"None (external VGA/DVI)\",\n" +
                "  \"os\": \"Mac OS X 10.3 Panther\"\n" +
                "}");
    }

    private void save(String brand, String model, int year, Era era, Condition condition,
                      double price, int stock, String description, String specs) throws SQLException {

        String imageUrl = com.leocodes.relikd.util.WikipediaImageFetcher.fetchImageUrl(brand, model);
        if (imageUrl != null) {
            System.out.println("  \u2713 Image found for " + brand + " " + model);
        } else {
            System.out.println("  \u2717 No image for " + brand + " " + model);
        }

        Computer computer = new Computer(
                0, brand, model, year, era, condition,
                price, stock, description, specs, imageUrl, LocalDateTime.now()
        );
        computerDAO.save(computer);
    }
}