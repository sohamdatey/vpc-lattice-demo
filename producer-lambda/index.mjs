export const handler = async (event) => {
    console.log("event:", event)
    // TODO implement
    const response = {
      statusCode: 200,
      body: JSON.stringify({message:"Hi from provider"}),
    };
    return response;
  };
  