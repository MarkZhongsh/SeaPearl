function setConfig(idfa) {
  var game = "PM001765"
  var channel = "apple"
  var system = "ios"
  var version = "1"
  var salt = "S$R(Nf)b#KP^y2rM8A@$4*7Ye3FZ&Gk%"
  var param = game+','+channel+','+idfa+','+system+','+version
  var md5Param = md5(param+salt)
  var base64 = btoa(param+','+md5Param)
  var url = "https://mixsdk.921.com/h5/play"
  console.log(base64)
  console.log(url+'?'+"param="+base64)
}