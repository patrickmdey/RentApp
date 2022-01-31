import {RentProposal} from "../../features/api/rentProposals/types";
import {Pagination} from "react-bootstrap";
import RequestCard from "./RequestCard";
import {useReducer, useState} from "react";


interface PendingRequestsProps {
    received: boolean
}

const range = (start:number, end:number) => Array.from(Array(end - start + 1).keys()).map(x => x + start);

export default function PendingRequests (props: PendingRequestsProps){
    const pendingRequests: RentProposal[] = [
        {
            articleUrl: new URL("http://pawserver.it.itba.edu.ar/paw-2021b-3/article/26"),
            state: 0,
            message: "holaaaaa",
            startDate: "2022-04-07",
            endDate: "2022-05-13",
            seen: false,
            url: new URL("http://pawserver.it.itba.edu.ar/paw-2021b-3/article/26"),
            renterUrl: new URL("http://pawserver.it.itba.edu.ar/paw-2021b-3/article/26")
        },
        ]


    const getPaginationItems = () => {
        const a = pendingRequests.slice(
            (currentPage-1) * pageSize,
            (currentPage) * pageSize
        );
        console.log((currentPage-1) * pageSize)
        console.log( (currentPage) * pageSize)
        return a;
    }

    const getPaginationData = () => range(
                                            currentPage - 2 >= 1 ? currentPage - 2 : 1,
                                            currentPage + 2 <= pages ?  currentPage + 2: pages
                                        )

    function handlePaginationUpdate(data: any){
        setCurrentPage(Number.parseInt(data.target.text))

    }

    function nextPage(){
        setCurrentPage((page) =>page == pages ? page : page+1)
    }

    function prevPage(){
        setCurrentPage((page) =>page == 1 ? page : page-1)
    }

    const [ currentPage, setCurrentPage ] = useState(1);
    const [pageSize] = useState(5)
    const [pages] = useState(Math.ceil(pendingRequests.length /pageSize))


    return (
        <div>
            {getPaginationItems().map((req,index) => <RequestCard key={index} {...req}/>)}
            <Pagination className="justify-content-center">
                <Pagination.Prev onClick={prevPage} disabled={currentPage == 1} />
                {
                    getPaginationData().map((page)=>

                        <Pagination.Item key={page} onClick={handlePaginationUpdate} active={currentPage == page}>
                            {page}
                        </Pagination.Item>

                    )
                }
                <Pagination.Next onClick={nextPage} disabled={currentPage == pages}/>
            </Pagination>
        </div>
    )
}