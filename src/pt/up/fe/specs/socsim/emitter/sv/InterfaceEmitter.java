package pt.up.fe.specs.socsim.emitter.sv;

import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.register.Register;

public class InterfaceEmitter implements Emitter {
    private StringBuilder sb;
    private final Module module;

    public InterfaceEmitter(Module module) {
        this.sb = new StringBuilder();
        this.module = module;
    }

    private void emitHeader() {
        this.sb.append("module ").append(this.module.name()).append(" #(\n");
    }

    private void emitParameters() {
        this.sb.append("    parameter type reg_req_t  = logic,\n");
        this.sb.append("    parameter type reg_rsp_t  = logic,\n");
        this.sb.append("    parameter type obi_req_t  = logic,\n");
        this.sb.append("    parameter type obi_resp_t = logic,\n");
        this.sb.append(") (\n");
    }

    private void emitPorts() {
        this.sb.append("    input logic clk_i,\n");
        this.sb.append("    input logic rst_ni,\n");

        if (module.interfaces().reg()) {
            this.sb.append("    input reg_req_t reg_req_i,\n");
            this.sb.append("    output reg_rsp_t reg_rsp_o,\n");
        }

        if (module.interfaces().obiMaster()) {
            this.sb.append("    output obi_req_t masters_req_o,\n");
            this.sb.append("    output obi_resp_t masters_resp_i,\n");
        }

        if (module.interfaces().obiSlave()) {
            this.sb.append("    input obi_req_t slave_req_i,\n");
            this.sb.append("    output obi_resp_t slave_resp_o,\n");
        }

        int lastComma = this.sb.lastIndexOf(",");
        if (lastComma != -1) this.sb.deleteCharAt(lastComma);

        this.sb.append(");\n\n");
    }

    private void emitRegisters() {
        for (Register reg : this.module.registers()) {
            this.sb.append("  ").append(reg.type().getType().equals("logic") ? "logic [" : "bit [")
                    .append(reg.width() - 1)
                    .append(":0] ")
                    .append(reg.name())
                    .append(";\n");
        }

        this.sb.append("\n");
    }

    private void emitDpiImports() {
        this.sb.append("  import \"DPI-C\" function void ").append(String.format("%s_comm_init();\n", module.name()));
        this.sb.append("  import \"DPI-C\" function void ").append(String.format("%s_comm_free();\n", module.name()));

        if (!this.module.dpi().send().isEmpty())
            this.sb.append("  import \"DPI-C\" function void ").append(String.format("%s_comm_send();\n", module.name()));

        if (!this.module.dpi().recv().isEmpty())
            this.sb.append("  import \"DPI-C\" function int ").append(String.format("%s_comm_recv();\n", module.name()));

        this.sb.append("\n");
    }

    private void emitInitialBlock() {
        this.sb.append("  initial begin\n");
        this.sb.append("    ").append(String.format("%s_comm_init();\n", module.name()));

        for (Register reg : module.registers())
            this.sb.append("    ")
                    .append(reg.name()).append(" = ").append(reg.initial().toString())
                    .append(";\n");

        this.sb.append("  end\n\n");
    }

    private void emitFinalBlock() {
        this.sb.append("  final begin\n");
        this.sb.append("    ").append(String.format("%s_comm_free();\n", module.name()));
        this.sb.append("  end\n\n");
    }

    private void emitDpiSendBlock() {
        if (this.module.dpi().send().isEmpty()) return;

        this.sb.append("  always_ff @(posedge clk_i or negedge rst_ni) begin\n");
        this.sb.append("    if (!rst_ni) begin\n").append("    end else begin\n");

        for (String s : this.module.dpi().send()) {
            this.sb.append("    ").append(String.format("  %s_comm_send(", this.module.name())).append(s).append(");\n");
        }

        this.sb.append("    end\n").append("  end\n\n");
    }

    private void emitDpiRecvBlock() {
        if (this.module.dpi().recv().isEmpty()) return;

        this.sb.append("  always_ff @(posedge clk_i or negedge rst_ni) begin\n")
                .append("    if (!rst_ni) begin\n");

        for (String s : this.module.dpi().recv()) {
            this.sb.append("      ").append(s).append(" <= 32'b0;\n");
        }

        this.sb.append("    end else begin\n");

        for (String s : this.module.dpi().recv()) {
            this.sb.append("      ").append(s).append(" <= ").append(String.format("%s_comm_recv();\n", this.module.name()));
        }

        this.sb.append("    end\n").append("  end\n\n");
    }

    private void emitFooter() {
        this.sb.append("endmodule\n");
    }

    @Override
    public String emit() {
        this.emitHeader();
        this.emitParameters();
        this.emitPorts();
        this.emitRegisters();
        this.emitDpiImports();
        this.emitInitialBlock();
        this.emitFinalBlock();
        this.emitDpiSendBlock();
        this.emitDpiRecvBlock();
        this.emitFooter();

        return this.sb.toString();
    }
}
