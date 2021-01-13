var callback = arguments[arguments.length-1];
window.scrollTo(0,1000000);
window.scrollTo(0,0);
setTimeout(callback, 30000);
