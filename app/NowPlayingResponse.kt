data class NowPlayingResponse(

	@field:SerializedName("results")
	val results: List<NowPlaying?>? = null
)

data class NowPlaying(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null
)

