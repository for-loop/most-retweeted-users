# Problem
**Objective:** Find a sufficiently small set of retweeted users that accounts for most if not all retweets in the network.

Some of the problems to consider include the following:

* Finding the "minimum set" is an NP Hard problem. The algorithm seeks to find "sufficiently small set" of vertices instead of the minimum.

# Approach

* A graph represents a network of Twitter users.
* A user is depicted as a vertex.
* A retweet is depicted as a directed edge.
* Greedy approach will be used to find the "sufficiently small set" of users.
