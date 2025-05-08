package pt.up.fe.specs.socsim.modifier.verilog;

import pt.up.fe.specs.socsim.emitter.Modifier;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.modifier.BaseModifier;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestHarnessPkgModifier extends BaseModifier {
    private final int index;

    public TestHarnessPkgModifier(Module module, String filepath) throws IOException {
        super(module, filepath);

        this.index = this.calculateNextIndex();
    }

    private int calculateNextIndex() {
        Pattern pattern = Pattern.compile("localparam EXT_NPERIPHERALS = (\\d+);");
        Matcher matcher = pattern.matcher(getCode());
        if (!matcher.find())
            throw new IllegalStateException("Could not find EXT_NPERIPHERALS in test harness package");

        return Integer.parseInt(matcher.group(1));
    }

    private String incrementPeripheralCount(String content) {
        return content.replaceFirst(
                "localparam EXT_NPERIPHERALS = \\d+;",
                "localparam EXT_NPERIPHERALS = " + (this.index + 1) + ";"
        );
    }

    private String addPeripheralConstants(String content) {
        String insertionPoint = "  localparam addr_map_rule_t [EXT_NPERIPHERALS-1:0] EXT_PERIPHERALS_ADDR_RULES";

        String moduleName = getModule().name();
        String moduleNameUpper = getModule().name().toUpperCase();
        String moduleOffset = getModule().offset();
        String moduleSize = getModule().size();

        String newConstants = String.format(
                "\n  // %s Peripheral\n" +
                        "  localparam logic [31:0] %s_START_ADDRESS = core_v_mini_mcu_pkg::EXT_PERIPHERAL_START_ADDRESS + 32'h0%s;\n" +
                        "  localparam logic [31:0] %s_SIZE = 32'h%s;\n" +
                        "  localparam logic [31:0] %s_END_ADDRESS = %s_START_ADDRESS + %s_SIZE;\n" +
                        "  localparam logic [31:0] %s_IDX = 32'd%d;\n\n",
                moduleName,
                moduleNameUpper, moduleOffset,
                moduleNameUpper, moduleSize,
                moduleNameUpper, moduleNameUpper, moduleNameUpper,
                moduleNameUpper, this.index
        );

        return content.replace(insertionPoint, newConstants + insertionPoint);
    }

    private String addToAddrMapRules(String content) {
        Pattern pattern = Pattern.compile("(.*EXT_PERIPHERALS_ADDR_RULES = '\\{.*?)(\\s*\\}\\s*;.*)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            String newEntry = String.format(
                    ",\n      '{idx: %s_IDX, start_addr: %s_START_ADDRESS, end_addr: %s_END_ADDRESS}",
                    getModule().name().toUpperCase(),
                    getModule().name().toUpperCase(),
                    getModule().name().toUpperCase()
            );

            return matcher.group(1) + newEntry + matcher.group(2);
        }
        throw new IllegalStateException("Could not find EXT_PERIPHERALS_ADDR_RULES array in test harness package");
    }

    @Override
    public String modifyToString() {
        String updatedCode = this.incrementPeripheralCount(getCode());
        updatedCode = this.addPeripheralConstants(updatedCode);
        updatedCode = this.addToAddrMapRules(updatedCode);

        return updatedCode;
    }

    @Override
    public void modifyToFile(String filepath) {

    }
}
