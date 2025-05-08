package pt.up.fe.specs.socsim.emitter.sv.tb;

import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.model.Module;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestHarnessPkgEmitter implements Emitter {
    private String code;
    private final Module module;
    private int nextIndex;

    public TestHarnessPkgEmitter(String path, Module module) throws IOException {
        this.code = this.getCode(path);
        this.module = module;
        this.nextIndex = -1;
    }

    private String getCode(String path) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("sv/test_harness_pkg.sv");

        if (inputStream == null) {
            throw new RuntimeException("File not found in resources!");
        }

        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    private static final Pattern PERIPHERAL_IDX_PATTERN =
            Pattern.compile("localparam logic \\[31:0\\] (\\w+)_IDX = 32'd(\\d+);");

    private int getCurrentPeripheralCount(String code) {
        Pattern pattern = Pattern.compile("localparam EXT_NPERIPHERALS = (\\d+);");
        Matcher matcher = pattern.matcher(code);

        if (matcher.find())
            return Integer.parseInt(matcher.group(1));

        throw new IllegalStateException("Could not find EXT_NPERIPHERALS in code");
    }

    private String incrementPeripheralCount(String code) {
        int currentCount = this.getCurrentPeripheralCount(code);

        this.nextIndex = currentCount + 1;

        return code.replaceFirst(
                "localparam EXT_NPERIPHERALS = \\d+;",
                "localparam EXT_NPERIPHERALS = " + (this.nextIndex) + ";"
        );
    }

    private String addPeripheralConstants(String code) {
        String insertionPoint = "  localparam addr_map_rule_t [EXT_NPERIPHERALS-1:0] EXT_PERIPHERALS_ADDR_RULES";

        String newConstants = String.format(
                "\n  // %s Peripheral\n" +
                        "  localparam logic [31:0] %s_START_ADDRESS = core_v_mini_mcu_pkg::EXT_PERIPHERAL_START_ADDRESS + 32'h%04x;\n" +
                        "  localparam logic [31:0] %s_SIZE = 32'h%x;\n" +
                        "  localparam logic [31:0] %s_END_ADDRESS = %s_START_ADDRESS + %s_SIZE;\n" +
                        "  localparam logic [31:0] %s_IDX = 32'd%d;\n",
                module.name(),
                module.name().toUpperCase(), module.offset(),
                module.name().toUpperCase(), module.size(),
                module.name().toUpperCase(), module.name().toUpperCase(), module.name().toUpperCase(),
                module.name().toUpperCase(), this.nextIndex - 1
        );

        return code.replace(insertionPoint, newConstants + insertionPoint);
    }

    private String addToAddrMapRules(String code) {
        Pattern pattern = Pattern.compile("(.*EXT_PERIPHERALS_ADDR_RULES = '\\{.*?)(\\s*\\}\\s*;.*)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(code);

        if (matcher.find()) {
            String prefix = matcher.group(1);
            String suffix = matcher.group(2);

            String newEntry = String.format(
                    ",\n      '{idx: %s_IDX, start_addr: %s_START_ADDRESS, end_addr: %s_END_ADDRESS}",
                    module.name().toUpperCase(),
                    module.name().toUpperCase(),
                    module.name().toUpperCase()
            );

            return prefix + newEntry + suffix;
        }
        throw new IllegalStateException("Could not find EXT_PERIPHERALS_ADDR_RULES array in code");
    }

    public String emit() {
        this.code = this.incrementPeripheralCount(this.code);

        this.code = this.addPeripheralConstants(this.code);

        this.code = this.addToAddrMapRules(this.code);

        return this.code;
    }

    @Override
    public String emitToString() {
        return null;
    }

    @Override
    public void emitToFile(String filepath) {

    }
}
