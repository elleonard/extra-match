@(id: String, tag: String)

$(function(){
  $.get("/stage/list.json", function(stages){
    $.each(stages.stages, function(key, type){
      $("@id").append($("@tag").text(type.stage))
    })
  })
})