$ ->
  $.get "/enemy/list.json", (json) ->
    $.each json.enemies, (key, enemy) ->
      $("#select_enemy").append $("<option>").attr("value","#{enemy["name"]}").text "#{enemy["name"]}"
