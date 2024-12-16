// Buscar Cidades por Raio
document.getElementById('form-busca').addEventListener('submit', function(event) {
    event.preventDefault();
    const latitude = parseFloat(document.getElementById('latitude').value);
    const longitude = parseFloat(document.getElementById('longitude').value);
    const raio = parseFloat(document.getElementById('raio').value);

    const url = `http://localhost:8080/cidades/buscar-por-raio?latitude=${latitude}&longitude=${longitude}&raio=${raio}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const listaCidades = document.getElementById('lista-cidades');
            listaCidades.innerHTML = '';
            data.forEach(cidade => {
                const li = document.createElement('li');
                li.textContent = `${cidade.nome} - Coordenadas: (${cidade.coordenada.x}, ${cidade.coordenada.y})`;
                listaCidades.appendChild(li);
            });
        })
        .catch(error => alert('Erro ao buscar as cidades. Tente novamente.'));
});

// Criar Cidade
document.getElementById('form-criar-cidade').addEventListener('submit', function(event) {
    event.preventDefault();
    const nome = document.getElementById('nome').value;
    const coordenada = document.getElementById('coordenada').value.split(',');
    const cidade = { nome, coordenada: { x: parseFloat(coordenada[0]), y: parseFloat(coordenada[1]) } };

    fetch('http://localhost:8080/cidades', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(cidade)
    })
        .then(response => response.json())
        .then(data => alert('Cidade criada com sucesso!'))
        .catch(error => alert('Erro ao criar a cidade.'));
});

// Obter Cidade por ID
document.getElementById('form-obter-cidade').addEventListener('submit', function(event) {
    event.preventDefault();
    const id = document.getElementById('cidade-id').value;

    fetch(`http://localhost:8080/cidades/${id}`)
        .then(response => response.json())
        .then(data => {
            const info = document.getElementById('cidade-info');
            info.textContent = `Nome: ${data.nome}, Coordenada: (${data.coordenada.x}, ${data.coordenada.y})`;
        })
        .catch(error => alert('Erro ao obter a cidade.'));
});

// Atualizar Cidade
document.getElementById('form-atualizar-cidade').addEventListener('submit', function(event) {
    event.preventDefault();
    const id = document.getElementById('cidade-id-atualizar').value;
    const nome = document.getElementById('nome-atualizar').value;
    const coordenada = document.getElementById('coordenada-atualizar').value.split(',');
    const cidade = { nome, coordenada: { x: parseFloat(coordenada[0]), y: parseFloat(coordenada[1]) } };

    fetch(`http://localhost:8080/cidades/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(cidade)
    })
        .then(response => response.json())
        .then(data => alert('Cidade atualizada com sucesso!'))
        .catch(error => alert('Erro ao atualizar a cidade.'));
});

// Deletar Cidade
document.getElementById('form-deletar-cidade').addEventListener('submit', function(event) {
    event.preventDefault();
    const id = document.getElementById('cidade-id-deletar').value;

    fetch(`http://localhost:8080/cidades/${id}`, { method: 'DELETE' })
        .then(response => {
            if (response.ok) alert('Cidade deletada com sucesso!');
        })
        .catch(error => alert('Erro ao deletar a cidade.'));
});

// Verificar se Cidade está dentro de um Polígono
document.getElementById('form-verificar-poligono').addEventListener('submit', function(event) {
    event.preventDefault();
    const id = document.getElementById('cidade-id-poligono').value;
    const wktPoligono = document.getElementById('wkt-poligono').value;

    fetch(`http://localhost:8080/cidades/${id}/verificar-dentro-poligono`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ wktPoligono })
    })
        .then(response => response.json())
        .then(data => {
            const resultado = document.getElementById('resultado-poligono');
            resultado.textContent = data ? 'Cidade está dentro do polígono.' : 'Cidade não está dentro do polígono.';
        })
        .catch(error => alert('Erro ao verificar a cidade.'));
});
