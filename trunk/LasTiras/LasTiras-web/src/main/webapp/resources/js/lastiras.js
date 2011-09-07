/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

(function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-25089632-1']);
_gaq.push(['_trackPageview']);

var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));

try{ 
    var pageTracker = _gat._getTracker("UA-25089632-1");
    pageTracker._trackPageview();
    
} catch(err) {
    
} 

var myArray = new Array();

function changeWidth(id,limit,imageUrl){
    this.x=document.getElementById(id);
    this.img = new Image();
    this.img.onload = function() {
        var value = this.width;
        if(value>limit){
            x.width=limit;
        }
        else{
            x.width=value;
        }
    }
    this.img.src=imageUrl;
}

function loadImage(imageUrl,idDocImage)
{
    for (i=0;i<myArray.length;i++){
        if(myArray[i]==idDocImage){
            return;
        }
    }
    myArray.push(idDocImage);
    var myDocumentImage = document.getElementById(idDocImage);
    var myImage = new Image();
     
    myImage.onload = function(){
        myDocumentImage.src=imageUrl;                
    }
    
    myImage.src=imageUrl;
}
