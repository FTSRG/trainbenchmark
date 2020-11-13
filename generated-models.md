---
layout: default
---

## Generated models

### EMF

{% for file in site.static_files %}{% if file.name contains ".xmi" %}* [{{ file.path | remove: "/models/railway-" }}]({{ site.baseurl }}{{ file.path }})
{% endif %}{% endfor %}


### Graph

#### CSV

{% for file in site.static_files %}{% if file.name contains ".csv" %}* [{{ file.path | remove: "/models/railway-" }}]({{ site.baseurl }}{{ file.path }})
{% endif %}{% endfor %}

#### GraphML (Neo4j)

{% for file in site.static_files %}{% if file.name contains "-neo4j.graphml" %}* [{{ file.path | remove: "/models/railway-" }}]({{ site.baseurl }}{{ file.path }})
{% endif %}{% endfor %}

#### GraphML (TinkerPop)

{% for file in site.static_files %}{% if file.name contains "-tinkerpop.graphml" %}* [{{ file.path | remove: "/models/railway-" }}]({{ site.baseurl }}{{ file.path }})
{% endif %}{% endfor %}

#### Cypher

{% for file in site.static_files %}{% if file.name contains ".cypher" %}* [{{ file.path | remove: "/models/railway-" }}]({{ site.baseurl }}{{ file.path }})
{% endif %}{% endfor %}

### RDF

#### Inferred

{% for file in site.static_files %}{% if file.name contains "-inferred.ttl" %}* [{{ file.path | remove: "/models/railway-" }}]({{ site.baseurl }}{{ file.path }})
{% endif %}{% endfor %}

#### Metamodel

{% for file in site.static_files %}{% if file.name contains "-metamodel.ttl" %}* [{{ file.path | remove: "/models/railway-" }}]({{ site.baseurl }}{{ file.path }})
{% endif %}{% endfor %}
