import RequestCard from "./RequestCard";
import { RentProposal } from "../../features/api/rentProposals/types";
function RequestCardList(requests: RentProposal[]) {
  return (
    <>
      {requests.map((req, i) => (
        <RequestCard key={i} {...req}></RequestCard>
      ))}
    </>
  );
}

export default RequestCardList;
