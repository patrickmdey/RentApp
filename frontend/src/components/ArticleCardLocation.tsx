import { GeoAltFill } from "react-bootstrap-icons";
import { useFindLocation } from "../features/api/locations/locationsSlice";

function ArticleCardLocation(props: { locationUrl: URL }) {
  const { data: location } = useFindLocation(props.locationUrl);

  return (
    <div className="display-flex">
      <GeoAltFill size="3vh" color="primary"></GeoAltFill>
      {location && <p>{location.name}</p>}
    </div>
  );
}

export default ArticleCardLocation;
