import fetch from 'node-fetch';
export const handler = async (event) => {
  // TODO implement
  console.log("consumer started")
  await fetch('http://demo-service-097fd8611bfa2ee2a.7d67968.vpc-lattice-svcs.us-east-1.on.aws', {
    method: 'POST',
    body: JSON.stringify({val: 10}),
    headers: {
      'Content-type': 'application/json; charset=UTF-8',
    },
})
  .then((response) => response.json())
  .then((json) => console.log(json));
  
  const response = {
    statusCode: 200,
    body: JSON.stringify('Hello from Lambda!'),
  };
  return response;
};


handler({test: "test"});