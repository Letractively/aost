var  attrValue = "onclick:_onButtonClick,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse";
var map = new HashMap();

    if(trimString(attrValue).length > 0 ){
        var split = attrValue.split(",");
        alert(split.length);
        for(var i=0; i<split.length; i++){
            if(trimString(split[i]).length > 0){
                var pair = trimString(split[i]).split(":");
                map.set(trimString(pair[0]), trimString(pair[1]));
                alert(split[i] + " --> " + pair[0] + ": " + pair[1]);
            }
        }
    }      
   alert("Map: " + map.showMe());

   var keySet = map.keySet();
   alert(keySet.length);

   for(var j=0; j<keySet.length; j++){
        alert("key: " + keySet[j] + ", value: " + map.get(keySet[j]));
   }