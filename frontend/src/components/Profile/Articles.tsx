import {Tab, Tabs} from "react-bootstrap";
import {useListArticles} from "../../api/articles/articlesSlice";
import {User} from "../../api/users/types";
import {strings} from "../../i18n/i18n";
import ArticleCardList from "../Article/ArticleCardList";
import usePaginatedResponse from "../../hooks/usePaginatedResponse";
import {useState} from "react";
import PagesList from "../PagesList";

export default function Articles(props: { user: User }) {
    const user = props.user;
    const [publishedArticlesPage, setPublishedArticlesPage] = useState(1);
    const [rentedArticlesPage, setRentedArticlesPage] = useState(1);
    const {data: publishedArticles, pages: publishedArticlesPages} = usePaginatedResponse(useListArticles(
        {user: user.id, page: publishedArticlesPage}));

    const {data: rentedArticles, pages: rentedArticlesPages} = usePaginatedResponse(useListArticles({
        renter: user.id, page: rentedArticlesPage
    }));

    const defaultActiveTab = user.owner ? "published" : "rented";

    return (
        <Tabs
            defaultActiveKey={defaultActiveTab}
            id="article_tabs"
            className="mb-3"
        >
            {user.owner && (
                <Tab
                    eventKey="published"
                    title={strings.collection.profile.createdArticles}
                >
                    <ArticleCardList
                        articles={publishedArticles || []}
                        articlesPerRow={3}
                    />
                    <PagesList pages={publishedArticlesPages} page={publishedArticlesPage}
                               setPage={setPublishedArticlesPage}/>
                </Tab>
            )}
            <Tab eventKey="rented" title={strings.collection.profile.rentedArticles}>
                <ArticleCardList
                    articles={rentedArticles || []}
                    articlesPerRow={3}
                /><PagesList pages={rentedArticlesPages} page={rentedArticlesPage}
                             setPage={setRentedArticlesPage}/>
            </Tab>
        </Tabs>
    );
}
