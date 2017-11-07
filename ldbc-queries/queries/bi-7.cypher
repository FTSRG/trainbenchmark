// Q7. Most authoritative users on a given topic
/*
  :param { tag: 'Franz_Liszt' }
*/
MATCH
  (tag:Tag {name: $tag})<-[:hasTag]-(:Message)-[:hasCreator]->(person1:Person)
MATCH
  (person1)<-[:hasCreator]-(message:Message)-[:hasTag]->(tag),
  (message)<-[:likes]-(person2:Person)<-[:hasCreator]-(:Message)<-[l:likes]-(person3:Person)
RETURN
  person1.id,
  count(l) AS authorityScore
//ORDER BY
//  authorityScore DESC,
//  person1.id ASC
//LIMIT 100
