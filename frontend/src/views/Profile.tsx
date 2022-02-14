import { skipToken } from "@reduxjs/toolkit/dist/query";
import { useState } from "react";
import { Card, Container } from "react-bootstrap";
import Articles from "../components/Profile/Articles";
import ProfileCard from "../components/Profile/ProfileCard";
import { useFindLocation } from "../api/locations/locationsSlice";
import { useFindUser } from "../api/users/usersSlice";
import useUserId from "../hooks/useUserId";
import Error from "../components/Error";

export default function Profile() {
  const id = useUserId();

  const {
    data: user,
    isLoading: userIsLoading,
    isSuccess: userIsSuccess,
    error,
  } = useFindUser(
    new URL(`users/${id}`, process.env.REACT_APP_BASE_URL).toString()
  );

  const { data: location } = useFindLocation(
    userIsLoading || user == null ? skipToken : user.locationUrl
  );
  const [disabled, setDisabled] = useState(true);

  if (error && "status" in error)
    return (
      <Error
        error={error.status}
        message={typeof error.data === "string" ? error.data : undefined}
      />
    );

  return (
    <Container>
      {user && location && (
        <ProfileCard
          disabled={disabled}
          setDisabled={setDisabled}
          location={location}
          user={user}
        />
      )}
      {user && disabled && (
        <Card className="w-100 p-3 mt-3">
          <Articles user={user} />
        </Card>
      )}
    </Container>
  );
}
