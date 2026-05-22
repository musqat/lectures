import { Suspense } from "react";
import MovieVideos from "../../../../components/movie-videos";
import MovieInfo from "../../../../components/movie-info";

export default async function MoviesDetail({
     params: { id },
    }: {
     params: { id:string };

    }) {
    return <div>
        <h3>Movie detail page</h3>
        <Suspense fallback={<h1>Loading movie info</h1>}>
        <MovieInfo id={id}/>
        </Suspense>
        <Suspense fallback={<h1>Loading movie video</h1>}>
        <MovieVideos id={id}/>
        </Suspense>
    </div>
}