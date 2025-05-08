package pt.up.fe.specs.socsim.emitter.sv.tb;

import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.model.Module;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestHarnessEmitter implements Emitter {
    private String code;
    private final Module module;

    public TestHarnessEmitter(String path, Module module) throws IOException {
        this.code = this.loadFile(path);
        this.module = module;
    }

    private String loadFile(String path) throws IOException {
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream(path);

        if (is == null)
            throw new RuntimeException("File " + path + " not found in resources!");

        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    private String generatePeripheralInstance() {
        boolean hasObiInterfaces =
                this.module.interfaces().obiSlave() ||
                        this.module.interfaces().obiMaster();

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
                    this.module.name(), this.module.name(), this.module.name().toUpperCase(), this.module.name().toUpperCase());
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
                this.module.name(), this.module.name(), this.module.name().toUpperCase(), this.module.name().toUpperCase());
    }

    @Override
    public String emit() {
        String referencePeripheral = "simple_accelerator";
        String patternString = String.format("\\s+%s\\s+#\\([\\s\\S]*?\\)\\s+%s_i\\s*\\([\\s\\S]*?\\)\\s*;",
                referencePeripheral, referencePeripheral);
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(code);

        if (!matcher.find()) {
            throw new IllegalStateException("Could not find reference peripheral '" +
                    referencePeripheral + "' in test_harness.sv");
        }

        int insertIndex = matcher.end();
        String peripheralInstance = this.generatePeripheralInstance();

        code = code.substring(0, insertIndex)
                + "\n\n" + peripheralInstance
                + code.substring(insertIndex);

        return code;
    }
}
