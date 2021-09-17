# Q & A from students 

| Class | Method | Return| What to implement     |  What to Consider              |
|------ | -------|-------|---------------------------------|----------------------------------------|
|Dataset | loadRatings() | number Of Ratings | Loads ratings from a file: Read from File using this.getRawFile().toFile(), instantiate new Rating object for each line, add to collection this.ratingList, and return final number of ratings in the list |  where is ratingList initialized and what to do if this.getRawFile() does not exist |  
|Dataset | loadStats() | number Of Stats | Loads statistics from a file: Read from File using inStatPath.toFile(), instantiate new RatingSummary object for each line, add to collection this.ratingStat, and return final number of entries |  return 0 if inStatPath does not exist |  
|Dataset | computeStats() | boolean | computes new statistics: create unique list of users and products from ratingList, creates new RatingSummary object for each and computes statistics | Compute RatingSummary values for each object by constructing RatingSummary using 	public RatingSummary(final String id, final List<Rating> rawRatings) constructor.      |
| DatasetHandler | printDB() | String | overwrite current data.csv | Use StringBuilder to create output string; each line can be printed using dataset.toString; use .replace method for string to put in correct dataId |
| DatasetHandler | saveDBToFile() | save output of printDB() to dbPath | Output exceptions |
| DatasetHandler | saveStatsToFile(dataID) |  print dataID stats to File| Look into DataAnalysis static fields for string builder | use .saveStats() Dataset method to save stats for specific collection, writeString to write to file, IO considerations |
| RatingSummary | avgScore() | float value | one line | difference between product and reviewer average |
| RatingSummary | sortStats() | float value | pick a value you want to use to sort statistics in the collection |  this.getList().get(0).floatValue()| 
| RatingSummary | collectStats(List<Rating>) | void | find productAvg and reviewerAvg for nodeID (reviewer or product) | separate product and review, write different methods, use streams, see how below (or PROJECT1.md, Step 4)  |
| Data Analysis | sortByDegree() | List | sort list | hint: inList.sort((AbstractRatingSummary r1, AbstractRatingSummary r2)->Long.compare(r2.getDegree(), r1.getDegree())); |
| Data Analysis | sortByAvgDiff() | List | sort list | Look above |
| Data Analysis | printReport() | String | [report_Test.csv](https://git.txstate.edu/CS3354/data/blob/master/report_TEST.csv) in [data](https://git.txstate.edu/CS3354/data/) repository for report format | use StringBuilder, and sublist option for collections to select topK |
  
## There is no deadline extension. 

## Methods to implement and what to implement

**DO NOT EDIT ** Rating, AbstractRatingSummary, and RatingStatsApp classes
  
## How to compute statistics 

ratingSummary_<DATA_ID>.csv example: 

```
Id,degree,product avg,reviewer avg,
A00625243BI8W1SSZNLMD,8,4.875,4.487
B00I3MPDP4,455,4.230,3.980
```
* Id (Column A): If the Id starts with letter B it is a **product**, if the Id starts with letter A it is a **reviewer** in the ratingSummary.csv. 
* degree (Column B): the number of times that specific Id shows up in the raw file.
* product avg. (Column C): the computation depends if it is a product or a review, see below
* reviewer avg (Column D): the computation depends if it is a product or a review, see below

We compute the statistics as follows: 

For **reviewer** (Id starts with letter A): 
* **reviewerID (Column A)
* **degree** marks the number of reviews from Id (appearance in raw file Column B). 
* Statistics of **all** possible reviews (from other reviewers and this reviewer) of **all** products **reviewer** scored is computed and stored as: 
  * product avg. (Column C)
* Statistics of all review scores **reviewer** provided is computed and stored as: 
  * reviewer avg (Column D)

For **product** (Id starts with letter B)
* **reviewerID (Column A)
* **degree** marks the number of reviews from Id (appearance in raw file Column B)
* Statistics of **all** possible review of the product is computed and stored as: 
  * product avg. (Column C)
* Statistics of **all** possible reviews from  **all** **reviewers** that reviewd this specific **product** is computed and stored as:
  * reviewer avg (Column D)
