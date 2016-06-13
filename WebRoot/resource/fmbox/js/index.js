$(document).ready(function(){


  var playlist = [{
      title:"Girl From Yesterday",
      artist:"",
      mp3:"a1.mp3",
	  /*下面是海报*/
      poster: "a1.jpg"
    },{
      title:"岚-Tank",
      artist:"",
      mp3:"a2.wav",
      poster: "002.jpg"
    },{
      title:"断点",
      artist:"",
      mp3:"a1.mp3",
      poster: "003.jpg"
    },];
	

  var cssSelector = {
    jPlayer: "#jquery_jplayer",
    cssSelectorAncestor: "#j1"
  };
  
  var options = {
    swfPath: "Jplayer.swf",
    supplied: "ogv, m4v, oga, mp3"
  };
  
  var myPlaylist = new jPlayerPlaylist(cssSelector, playlist, options);
  
   var cssSelector1 = {
    jPlayer: "#jquery_jplayer1",
    cssSelectorAncestor: "#j2"
  };
  var myPlaylist = new jPlayerPlaylist(cssSelector1, playlist, options);
});