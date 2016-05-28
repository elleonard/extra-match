$ ->
  search = (stage) ->
    $.get "/enemy/#{stage}/search.json", (json) ->
      $.each json.enemies, (key, enemy) ->
        $("#select_enemy").append $("<option>").attr("value", "#{enemy["name"]}").text "#{enemy["name"]}"
  $("#select_stage").change () ->
    $("#select_enemy").children().remove()
    search($("#select_stage option:selected").text())

