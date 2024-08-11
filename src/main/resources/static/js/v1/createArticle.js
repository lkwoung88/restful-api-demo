// create article
document.addEventListener("DOMContentLoaded", function () {
    const createApi = sessionStorage.getItem('createApi');
    const form = document.getElementById('content-form');
    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(form);
        const data = {
            title: formData.get('title'),
            content: formData.get('content')
        };

        fetch( createApi, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                alert('Article created successfully!');
                form.reset();
                window.location.href = '/';
            })
            .catch(error => {
                alert('Failed to create article.');
                window.location.href = '/';
            });
    });
});

