function loadCharacterList(){
  $.getJSON("character", function(characterList){
    console.log(characterList);
    renderCharacterList(characterList);
  });
}

function renderCharacterList(characterList) {
    var table = $("<table/>");

    $.each(characterList, function(index, character){
      console.log("index=" + index + ", character=" + character + ", name=" + character.name);
      var tr = createCharacterRow(character)
      if (index % 2 == 1)
        tr.addClass("odd");
      table.append(tr);
    });

    var div = $("#character-list-div");
    div.empty();
    div.append(table);
}

function createCharacterRow(character) {
  var tr = $("<tr/>");
  var tdId = $("<td/>"), tdName = $("<td/>"), tdNickname = $("<td/>"), tdCity = $("<td/>"), tdOperate = $("<td/>");
  var a = $("<a/>");

  tdId.html(character.id);
  tdName.html(character.name);
  tdNickname.html(character.nickname);
  tdCity.html(character.city);
  a.html("delete");
  a.attr("href", "JavaScript:void(0);");
  a.click(character, deleteAndReload);

  tdOperate.append(a);
  tr.append(tdId);
  tr.append(tdName);
  tr.append(tdNickname);
  tr.append(tdCity);
  tr.append(tdOperate);
  return tr;
}

function deleteAndReload(event){
  var character = event.data;
  console.log("delete character:" + character + ", id:" + character.id)
  $.ajax({
    url: "character/" + character.id,
    method:"DELETE",
    success: function(response, httpStatus) {
      console.log(response);
      alert("Deleted:" + httpStatus);
      loadCharacterList();
    },
    error: function(response, httpStatus) {
      console.log(response);
      alert("Deleted:" + httpStatus);
    }
  });
}

function addCharacter(){
  var character = gatherNewCharacterData();
  $.ajax({
    type:"POST",
    url: "character",
    data: JSON.stringify(character),
    contentType:"application/json",
  }).done(function(response, httpStatus){
      console.log(response);
      alert("Add: " + httpStatus);
      loadCharacterList();
  }).fail(function(response, httpStatus){
      console.log(response);
      alert("Add: " + httpStatus);
  });
}

function gatherNewCharacterData(){
  var character = {};
  character.name = $("#name").val();
  character.nickname = $("#nickname").val();
  character.city = $("#city").val();
  return character;
}