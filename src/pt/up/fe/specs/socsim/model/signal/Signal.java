package pt.up.fe.specs.socsim.model.signal;

public sealed interface Signal permits ClockSignal, ObiSignal, RegSignal, ResetSignal { }
