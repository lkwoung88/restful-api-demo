document.addEventListener("DOMContentLoaded", function () {
    const promise = fetch('/api-v1/article')
        .then(response => response.json())
        .then(data => {
            const createAPI = data._links.create.href;
            sessionStorage.setItem('createApi', createAPI);

            const articles = data._embedded ? data._embedded.articleList : null;
            if (articles) {
                const tableBody = document.querySelector('#contents-table tbody');

                articles.forEach(article => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                            <td>${article.id}</td>
                            <td>${article.title}</td>
                            <td>${article.content}</td>
                        `;
                    row.addEventListener('click', function () {
                        sessionStorage.setItem('self', article._links.self.href);
                        sessionStorage.setItem('delete', article._links.delete.href);
                        sessionStorage.setItem('update', article._links.update.href);
                        window.location.href = `./article/details/${article.id}`;
                    });
                    tableBody.appendChild(row);
                })
            }
        })
});