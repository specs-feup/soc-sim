package pt.up.fe.specs.socsim.modifier.verilog;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.modifier.BaseModifier;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestHarnessModifier extends BaseModifier {

    private static final String REFERENCE_PERIPHERAL = "simple_accelerator";

    public TestHarnessModifier(Module module, String filepath) throws IOException {
        super(module, filepath);
    }

    private String generatePeripheralInstance() {
        boolean hasObiInterfaces = getModule().interfaces().obiSlave() ||
                getModule().interfaces().obiMaster();

        String moduleName = getModule().name();
        String moduleUpper = moduleName.toUpperCase();

        if (hasObiInterfaces) {
            return String.format(
                    "      %s #(\n" +
                            "          .reg_req_t (reg_pkg::reg_req_t),\n" +
                            "          .reg_rsp_t (reg_pkg::reg_rsp_t),\n" +
                            "          .obi_req_t (obi_pkg::obi_req_t),\n" +
                            "          .obi_resp_t(obi_pkg::obi_resp_t)\n" +
                            "      ) %s_i (\n" +
                            "          .clk_i,\n" +
                            "          .rst_ni,\n" +
                            "          .reg_req_i(ext_periph_slv_req[testharness_pkg::%s_IDX]),\n" +
                            "          .reg_rsp_o(ext_periph_slv_rsp[testharness_pkg::%s_IDX]),\n" +
                            "          .masters_req_o(),\n" +
                            "          .masters_resp_i('0),\n" +
                            "          .slave_req_i('0),\n" +
                            "          .slave_resp_o()\n" +
                            "      );",
                    moduleName, moduleName, moduleUpper, moduleUpper
            );
        }

        return String.format(
                "      %s #(\n" +
                        "          .reg_req_t (reg_pkg::reg_req_t),\n" +
                        "          .reg_rsp_t (reg_pkg::reg_rsp_t)\n" +
                        "      ) %s_i (\n" +
                        "          .clk_i,\n" +
                        "          .rst_ni,\n" +
                        "          .reg_req_i(ext_periph_slv_req[testharness_pkg::%s_IDX]),\n" +
                        "          .reg_rsp_o(ext_periph_slv_rsp[testharness_pkg::%s_IDX])\n" +
                        "      );",
                moduleName, moduleName, moduleUpper, moduleUpper
        );
    }

    private String insertPeripheralInstance(String content, String peripheralInstance) {
        String patternString = String.format(
                "\\s+%s\\s+#\\([\\s\\S]*?\\)\\s+%s_i\\s*\\([\\s\\S]*?\\)\\s*;",
                REFERENCE_PERIPHERAL, REFERENCE_PERIPHERAL
        );

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);

        if (!matcher.find()) {
            throw new IllegalStateException(
                    "Could not find reference peripheral '" + REFERENCE_PERIPHERAL +
                            "' in test_harness.sv"
            );
        }

        int insertIndex = matcher.end();
        return content.substring(0, insertIndex) +
                "\n\n" + peripheralInstance +
                content.substring(insertIndex);
    }

    @Override
    public String modifyToString() {
        String code = getCode();
        String peripheralInstance = generatePeripheralInstance();

        return insertPeripheralInstance(code, peripheralInstance);
    }
}
