// Q11. Unrelated replies
// blacklists are omitted for now
/*
  :param {
    country: 'Pakistan'
  }
*/
MATCH
  (country:Country {name: $country})<-[:isPartOf]-(:City)<-[:isLocatedIn]-
  (person:Person)<-[:hasCreator]-(message:Message)<-[:replyOf]-(reply:Comment),
  (message)-[:hasTag]->(tag:Tag),
  (fan:Person)-[:likes]->(reply)
WHERE NOT (tag)<-[:hasTag]-(reply)
RETURN
  person.id,
  tag.name,
  count(fan) AS countLikes,
  count(reply) AS countReplies,
  reply.content
//ORDER BY
//  countLikes DESC,
//  person.id ASC,
//  tag.name ASC
//LIMIT 100
