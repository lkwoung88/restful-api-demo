document.addEventListener("DOMContentLoaded", function () {
    const pathSegments = window.location.pathname.split('/');
    const articleId = pathSegments[pathSegments.length - 1];

    const detailApi = sessionStorage.getItem('self');
    const deleteApi = sessionStorage.getItem('delete');
    const updateApi = sessionStorage.getItem('update');

    fetch(detailApi)
        .then(response => response.json())
        .then(article => {
            document.getElementById('title').value = article.title;
            document.getElementById('content').value = article.content;
            document.getElementById('createdAt').value = new Date(article.createdAt).toLocaleString();
            document.getElementById('updatedAt').value = new Date(article.updatedAt).toLocaleString();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to load article details.');
        });

    document.getElementById('update-button').addEventListener('click', function(event) {
        event.preventDefault();
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;

        fetch(updateApi, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id:articleId, title: title, content: content })
        })
            .then(response => {
                if (response.ok) {
                    alert('Article updated successfully');
                    window.location.href = `/article/details/${articleId}`;
                } else {
                    alert('Failed to update article');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to update article');
            });
    });

    document.getElementById('delete-button').addEventListener('click', function(event) {
        event.preventDefault();
        if (confirm('Are you sure you want to delete this article?')) {
            fetch(deleteApi, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        alert('Article deleted successfully');
                        window.location.href = '/';
                    } else {
                        alert('Failed to delete article');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Failed to delete article');
                });
        }
    });
});