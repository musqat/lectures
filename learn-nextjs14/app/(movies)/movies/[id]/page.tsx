import { Suspense } from "react";
import MovieVideos from "../../../../components/movie-videos";
import MovieInfo, { getMovie } from "../../../../components/movie-info";

interface IPrarams{
    params : {id:string},
}

export async function generateMetadata({params:{id}} : IPrarams) {
    const movie = await getMovie(id)
    return{
        title: movie.title,
    }
}


export default async function MoviesDetail({ params: {id} } : IPrarams) {
    return <div>
        <Suspense fallback={<h1>Loading movie info</h1>}>
        <MovieInfo id={id}/>
        </Suspense>
        <Suspense fallback={<h1>Loading movie video</h1>}>
        <MovieVideos id={id}/>
        </Suspense>
    </div>
}