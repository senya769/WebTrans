var cargo = [[${countAllTransport}]];
var ts = [[${countAllCargo}]];
var options = {
    useEasing : true,
    useGrouping : true,
    separator : ',',
    decimal : '.',
    prefix : '',
    suffix : ''
};
var counter = new CountUp("cargo", 0, cargo, 0, 3.5, options);
counter.start();
var counters = new CountUp("ts", 0, ts, 0, 3.5, options);
counters.start();
