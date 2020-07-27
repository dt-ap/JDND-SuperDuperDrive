$(document).ready(() => {
  var url = location.href.replace(/\/$/, '');

  var noteModal = $('#noteModal');
  var noteId = $('#noteId');
  var noteTitle = $('#noteTitleInput');
  var noteDescription = $('#noteDescInput');

  var credModal = $('#credentialModal');
  var credId = $('#credId');
  var credUrl = $('#credUrlInput');
  var credUsername = $('#credUsernameInput');
  var credPassword = $('#credPasswordInput');

  var deleteModal = $('#deleteModal');
  var deleteForm = $('#deleteModalForm');
  var deleteLabel = $('#deleteModalLabel');
  var deleteBody = $('#deleteModalText');
  var deleteId = $('#deleteModalId');

  if (location.hash) {
    const hash = url.split("#");
    $('#nav-' + hash[1] + '-tab').tab("show");
    url = location.href.replace(/\/#/, "#");
    history.replaceState(null, null, url);
    setTimeout(() => {
      $(window).scrollTop(0);
    }, 400);
  } else {
    $('#nav-files-tab').tab('show');
  }

  $('a[data-toggle="tab"]').click(function() {
    var newUrl;
    const hash = $(this).attr('href');
    newUrl = url.split('#')[0] + '#' + hash.split('-')[1];
    newUrl += '/';
    history.replaceState(null, null, newUrl);
  });

  noteModal.on('show.bs.modal', function(ev) {
    var btn = $(ev.relatedTarget);
    var id = btn.data('id') || '';
    var title = btn.data('title') || '';
    var description = btn.data('description') || '';

    changeNoteModal(id, title, description);
  });

  noteModal.on('hidden.bs.modal', function() {
    changeNoteModal();
  });

  credModal.on('show.bs.modal', function(ev) {
    var btn = $(ev.relatedTarget);
    var id = btn.data('id') || '';
    var url = btn.data('url') || '';
    var username = btn.data('username') || '';
    var password = btn.data('password') || '';

    changeCredModal(id, url, username, password);
  });

  credModal.on('hidden.bs.modal', function() {
    changeCredModal();
  });

  deleteModal.on('show.bs.modal', function(ev) {
    var btn = $(ev.relatedTarget);
    var title = btn.data('title') || '';
    var body = btn.data('body') || '';
    var action = btn.data('action') || '';

    changeDeleteModal(title, body, action);
  });

  deleteModal.on('hidden.bs.modal', function() {
    changeDeleteModal();
  });

  function changeNoteModal(id, title, description) {
    noteId.val(id || '');
    noteTitle.val(title || '');
    noteDescription.val(description || '');
  }

  function changeCredModal(id, url, username, password) {
    credId.val(id || '');
    credUrl.val(url || '');
    credUsername.val(username || '');
    credPassword.val(password || '');
  }

  function changeDeleteModal(title, body, action) {
    deleteBody.text(body || '');
    deleteLabel.text(title || '');
    deleteForm.attr('action', action || '#');
  }
});