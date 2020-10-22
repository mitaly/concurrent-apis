# concurrent-apis
 APIs for doing concurrent processing on data.
 1) Start API : To start processing of a given data.
         If the data is already under process by another request then invalidate the first request.
 2) End API : To stop processing of a given data.
         If the data processing has already been disrupted by another request then return an appropriate message.
