import { useListArticles } from "../features/api/articles/articlesSlice";
import ArticleCard from "../components/ArticleCard";

function Home() {
  const { data, error, isLoading } = useListArticles({});

  return (
    <div>
      <h1>MarketPlace</h1>
      {error ? (
        <>Oh no, there was an error</>
      ) : isLoading ? (
        <>Loading</>
      ) : (
        data &&
        data.map((article, index) => <ArticleCard key={index} {...article} />)
      )}
    </div>
  );
}

export default Home;
