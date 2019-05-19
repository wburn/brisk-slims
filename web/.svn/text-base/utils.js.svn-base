var slidedRow = null;
var otherSlidedRow = null;
var valid = false;

function insertAfterNode(elementToInsert, element)
{
  var parent = element.parentNode;
  var nextElement = element.nextSibling;

  if (parent)
    if (nextElement) parent.insertBefore(elementToInsert, nextElement);
    else parent.appendChild(elementToInsert);
}

function createNewElement(container, objectType)
{
  var object = document.createElement(objectType);

  if (container != null) container.appendChild(object);

  return object;
}

function createText(textValue)
{
  if (textValue == null) return null;

  var text = document.createElement("a");
  text.innerHTML = textValue;

  return text;
}

function addText(container, textValue)
{
  var text = createText(textValue);

  if (text != null) container.appendChild(text);

  return text;
}

function rowAddCell(row, image, href, hrefText, text)
{
  var cell = row.insertCell(0);
  addImage(cell, image);
  addHref(cell, href, hrefText);
  addText(cell, text);

  return cell;
}

function addImage(container, imageLink)
{
  if (imageLink == null) return null;

  var image = createNewElement(container, 'img');	image.src = imageLink;

  return image;
}

function addHref(container, href, hrefText)
{
	if (href == null) return null;

	var text = addText(container, hrefText); text.href = href;

	return text;
}

function addButton(container, buttonText, buttonHref)
{
  var button = createNewElement(createNewElement(container, 'div'), 'button');

  addText(button, buttonText).href =  buttonHref;

  return button
}

function removeAllChildNodes(node)
{
  if (node && node.hasChildNodes && node.removeChild)
	  while (node.hasChildNodes()) node.removeChild(node.firstChild);
}

function actionShowRelations(objectId, colnum)
{
  if (objectId == null) {  alert("object is null"); return; }

  var image = document.getElementById('I-'+objectId);

  if (image.src.match('images/up_10.gif') != null)
  {
    var row = document.getElementById('removeOnClick-' + objectId);
    image.src='./images/down_10.gif';
    row.parentNode.removeChild(row);
  }
  else //if (image.src.match('down_10.gif') != null)
  {
    var cell = image.parentNode;
    cell = cell.parentNode;       //new!! A.Z.
    if (cell == null)  {  alert("cell is null"); return; }
    image.src = './images/up_10.gif';
    var row = cell.parentNode;
    cln = row.className;
    var newRow = document.createElement('TR');
    insertAfterNode(newRow, row);
    newRow.id = 'removeOnClick-' + objectId;
    if (colnum > 0){
      cell = createNewElement(newRow, 'TD'); cell.colSpan = colnum;;
    }
    cell = createNewElement(newRow, 'TD'); cell.colSpan = 3;
    var cont = createNewElement(cell, 'A');
    var table = createNewElement(cont, 'TABLE');
    table.className = cln;

    var i = 0;
    var rel = document.getElementById(objectId + '-' + i);
    while (rel != null)
    {
      row = table.insertRow(i);
      cell = createNewElement(row, 'TD'); addText(cell, rel.value);
      cell = createNewElement(row, 'TD'); addText(cell, ' of ');
      cell = createNewElement(row, 'TD'); addText(cell, rel.name);
      i++; rel = document.getElementById(objectId + '-' + i);
    }
  }
}

function initSlidedHeader()
{
  slidedRow = document.getElementById("slidedHeader");

  if (slidedRow == null) return;

  otherSlidedRow = nextRow(slidedRow);
  removeSlidedHeader();

  valid = true;
}

function showSlidedHeader(cellId)
{
  if (valid == false) return;
  if (cellId == null) {  alert("cellId is null"); return; }

  var cell = document.getElementById(cellId);

  if (cell == null) {  alert("cell object is null"); return; }

  var row = cell.parentNode;

  if (row == null) {  alert("row object is null"); return; }

  var next = nextRow(row);

  var image = document.getElementById("headerImg_"+ cellId);

  if (image == null) {  alert("image is null"); return; }

  if (image.src.match('up_10_b.gif') != null)
  {
    var table = row.parentNode;

    if (slidedRow.parentNode != null)
    {
      var next = (otherSlidedRow != null) ? nextRow(otherSlidedRow) : nextRow(slidedRow);

      if (next != null)
      {
        var nextImage = document.getElementById("headerImg_"+ firstCell(next).id);

        if (nextImage != null)
        {
          nextImage.src = "./images/up_10_b.gif";

          var str = image.title.replace(/hide/,"show");
          nextImage.title = str;
        }
      }
    }

    image.src = "./images/down_10_b.gif";
    var str = image.title.replace(/show/,"hide");
    image.title = str;

    table.insertBefore(slidedRow, row);

    if (otherSlidedRow != null) table.insertBefore(otherSlidedRow, row);
  }
  else
  {
    image.src = "./images/up_10_b.gif";
    image.title = "show header";
    removeSlidedHeader();
  }
}

function removeSlidedHeader()
{
  if (slidedRow == null) return;

  var table = slidedRow.parentNode;

  if (otherSlidedRow != null) table.removeChild(otherSlidedRow);

  table.removeChild(slidedRow);
}

function firstCell(row)
{
  if (row == null) {  alert("row is null"); return null; }

  var cell = row.cells[0];

//alert(cell);

  if (cell == null) alert("first cell is null");

  return cell;
}

function nextRow(row)
{
  if (row == null) {  alert("row is null"); return; }

  var nextObj = row.nextSibling;

  while (nextObj != null)
  {
    //alert(nextObj + "  " + nextObj.className);
    if (nextObj.tagName == "TR") return nextObj;

    nextObj = nextObj.nextSibling;
  }

  return null;
}

function prevRow(row)
{
  if (row == null) {  alert("row is null"); return; }

  var prevObj = row.previousSibling;

  while (prevObj != null)
  {
    //alert(prevObj + "  " + nextObj.className);
    if (prevObj.tagName == "TR") return prevObj;

    prevObj = prevObj.previousSibling;
  }

  return null;
}

  function toOn(image)
  {
    image.src="./images/" + image.className + "On.gif";
  }

  function toDown(image)
  {
    image.src="./images/" + image.className + "Down.gif";
  }

  function toOut(image)
  {
    image.src="./images/" + image.className + ".gif";
  }

  function toHide(image)
  {
    image.src="./images/" + image.className + "Hidden.gif";
  }

  function jumpFocus(anchorId)
  {
    var x = document.getElementById(anchorId)

    if (x) scrollTo(0,x.offsetTop);
  }

  function openPermissionsDoc(){
       window.open('./Permissions.xls','',
        'scrollbars=yes,resizable=yes,menubar=no,toolbar=no,location=no,status=no');
}
  function disableAllButBack(){
        var f = document.getElementsByTagName('input');
        for(var i=0;i<f.length;i++){
            if(f[i].getAttribute('name')!=null 
                && f[i].getAttribute('name')!='badPermGoBack'
                && f[i].getAttribute('name')!='getPermissionDoc'){
                f[i].setAttribute('disabled',true)
            }
        }
        f = document.getElementsByTagName('select');
        for(var i=0;i<f.length;i++){
                f[i].setAttribute('disabled',true)
        }
        f = document.getElementsByTagName('textarea');
        for(var i=0;i<f.length;i++){
                f[i].setAttribute('disabled',true)
        }
  }