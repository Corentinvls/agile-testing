// ######################################################
// This is an example module provided by Wraith.
// Feel free to amend for your own requirements.
// ######################################################
module.exports = function (casper, ready) {
    casper.wait(10000,document.querySelector("#block-tesla-frontend-content > div > div > div > div > ul > li:nth-child(2)").click());
    // make Wraith wait a bit longer before taking the screenshot
    casper.wait(200000, ready); // you MUST call the ready() callback for Wraith to continue
}