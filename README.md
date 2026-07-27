# marketd - Asynchronous Web Scraper & TCO Indexer

O `marketd` é um *daemon* desenvolvido em Java (Spring Boot) focado na extração e indexação de dados não estruturados de classificados de veículos. Ele opera silenciosamente em background, contornando bloqueios de bots através de heurísticas de evasão, e expõe as oportunidades de mercado via API REST.

## ⚠️ Disclaimer Educacional

Este software foi construído estritamente para fins de pesquisa em Engenharia de Software, focando no estudo de *Parsing* de árvores DOM, Padrões de Projeto (Strategy/Observer) e Evasão de Sistemas Anti-Bot. O autor **não** incentiva a violação dos Termos de Serviço de plataformas comerciais e se isenta de qualquer responsabilidade sobre o uso indevido, massivo ou comercial desta ferramenta (vide Licença MIT).

---

## 🧠 Decisões Arquiteturais

Tutoriais básicos de web scraping falham no mundo real porque geram código acoplado e são bloqueados por firewalls na primeira execução. O `marketd` resolve isso através de engenharia:

* **Padrão Strategy & Template Method:** O núcleo de raspagem (`ScraperStrategy`) é agnóstico. Adicionar um novo site alvo requer apenas a criação de uma nova classe estendendo `BaseScraper`, sem alterar a lógica de orquestração (Princípio Open-Closed).
* **Desacoplamento por Eventos (Observer):** A camada de extração (Jsoup) não conhece o Banco de Dados. Ao extrair um dado válido, ela dispara um `VehicleScrapedEvent`. O banco de dados consome esse evento de forma independente.
* **Engenharia de Evasão (Jittering):** Requisições programáticas perfeitas atraem o *banhammer* de serviços como Cloudflare. O sistema implementa *Jittering* (pausas assimétricas e randômicas entre requisições) combinado com *User-Agent Spoofing* rotativo para imitar o comportamento orgânico de um navegador humano.
* **Persistência Leve:** Utiliza SQLite embutido, ideal para a proposta de um *daemon* local, sem a necessidade de levantar clusters pesados de banco de dados.

## 🛠️ Stack Tecnológica

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3 (Web, Data JPA)
* **Web Scraping:** Jsoup
* **Banco de Dados:** SQLite
* **Infraestrutura:** Docker (Multi-stage build)

---

## 🚀 Como Executar (Docker)

O projeto foi projetado para rodar em isolamento. Você não precisa ter o Java ou o Maven instalados na sua máquina, apenas o Docker.

1. Clone o repositório:

```bash
git clone [https://github.com/SeuUsuario/marketd.git](https://github.com/SeuUsuario/marketd.git)
cd marketd
```
1. Suba o Daemon em background:

```bash
docker compose up -d --build
```
1. Caso queira acompanhar os logs de extração

```bash
docker compose logs -f
```

## 📡 Consumo da API

Com o daemon rodando e populando o banco de dados no escuro, você pode consultar o veredito matemático das melhores opções encontradas consumindo o endpoint REST.

```bash
curl -s http://localhost:8080/api/vehicles
```

Exemplo de resposta:

```JSON
[
  {
    "id": 1,
    "title": "Royal Enfield Hunter 350 Dapper Ash",
    "price": 19500.00,
    "year": 2024,
    "mileage": 1200,
    "url": "[https://url-do-anuncio.com/](https://url-do-anuncio.com/)...",
    "source": "MERCADO_LIVRE",
    "scrapedAt": "2026-07-27T14:30:00"
  }
]
      ```


