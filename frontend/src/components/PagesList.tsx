import parse from "parse-link-header";
import { Pagination } from "react-bootstrap";

export default function PagesList(props: {
  pages: parse.Links | null;
  page: number;
  setPage: Function;
}) {
  const { pages, page, setPage } = props;
  if (pages == null) return null;

  const firstPage = parseInt(pages.first.page);
  const lastPage = parseInt(pages.last.page);
  const amount = lastPage - firstPage + 1;
  return (
    <div className="d-flex w-100 justify-content-center">
      <Pagination>
        {page > firstPage && (
          <Pagination.Prev onClick={() => setPage(page - 1)} />
        )}
        {[...Array(amount)].map((_, idx) => (
          <Pagination.Item
            onClick={() => setPage(idx + 1)}
            active={page === idx + 1}
          >
            {idx + 1}
          </Pagination.Item>
        ))}
        {page < lastPage && (
          <Pagination.Next onClick={() => setPage(page + 1)} />
        )}
      </Pagination>
    </div>
  );
}
