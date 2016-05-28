$ ->
  searchPatterns = (enemy, p1, p2, p3, p4, p5, p6) ->
    $.get "/pattern/search.json", { enemy: "#{enemy}", pattern1: "#{p1}", pattern2: "#{p2}", pattern3: "#{p3}", pattern4: "#{p4}", pattern5: "#{p5}", pattern6: "#{p6}" }, (json) ->
      patterns = $.each json.patterns, (key, p) ->
        record = "<tr><td>#{p.pattern1}</td><td>#{p.pattern2}</td><td>#{p.pattern3}</td><td>#{p.pattern4}</td><td>#{p.pattern5}</td><td>#{p.pattern6}</td></tr>"
        $("#result_table").append record

  searchEnemy = (stage) ->
    $.get "/enemy/#{stage}/search.json", (json) ->
      $.each json.enemies, (key, enemy) ->
        $("#select_enemy").append $("<option>").attr("value", "#{enemy["name"]}").text "#{enemy["name"]}"
      json.enemies[0]["name"]

  updatePatterns = (enemy) ->
    $("#result_table").children().remove()
    trTag = $("#result_table").append $("<tr>")
    for i in [1...7]
      trTag.append $("<th>").text "#{i}"
    searchPatterns(enemy, $("#pattern1 option:selected").text(), $("#pattern2 option:selected").text(), $("#pattern3 option:selected").text(), $("#pattern4 option:selected").text(), $("#pattern5 option:selected").text(), $("#pattern6 option:selected").text())

  updateEnemies = (enemy) ->
    $("#select_enemy").children().remove()
    $("#result_table").children().remove()
    updatePatterns(enemy)

  $("#select_enemy").change () ->
    enemy = $("#select_enemy option:selected").text()
    updatePatterns(enemy)

  $("#select_stage").change () ->
    updateEnemies(searchEnemy($("#select_stage option:selected").text()))

  getFirstStage = (callback) ->
    $.get "/stage/list.json", (json) ->
      $.each json.stages, (key, value) ->
        $("#select_stage").append $("<option>").text(value.stage)
      callback(json.stages[0]["stage"])

  getFirstStage(searchEnemy)


