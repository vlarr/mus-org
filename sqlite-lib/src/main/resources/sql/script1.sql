UPDATE R_MUS_INFO
set SOURCE_TAGS =
        (
            select rts.NAME
            from R_TRACK_SOURCES rts
            where rts.ID = R_MUS_INFO.SOURCE_ID
        )
where SOURCE_TAGS is null