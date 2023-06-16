
    const newUserRadio = document.getElementById('newUser');
    const mechanicRadio = document.getElementById('mechanic');
    const mechanicFields = document.getElementById('mechanicFields');
    // alert('js works')
    toggleUserType();

    newUserRadio.addEventListener('click', toggleUserType);
    mechanicRadio.addEventListener('click', toggleUserType);

    function toggleUserType() {
        if (newUserRadio.checked) {
            mechanicFields.style.display = 'none';
        } else if (mechanicRadio.checked) {
            mechanicFields.style.display = 'block';
        }
    }

