print("Started adding the users.");
db = db.getSiblingDB("admin");
db.createUser({
  user: "userx",
  pwd: "1234",
  roles: [{ role: "readWrite", db: "admin" }],
});
print("End adding the user roles.");
