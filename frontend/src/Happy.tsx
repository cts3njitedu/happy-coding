import { useEffect, useState } from "react";
import $ from 'jquery';

export default function Happy() {
    const [serverUrl] = useState('/api/poly/loadV2');

    useEffect(() => {
        $.ajax({
            url: serverUrl,
            method: "POST",
            data: {},
            dataType: 'script',
            contentType: "application/json",
            success: function(resp) {
                console.log("Response Successful", resp)
                console.log(PSTP);
            },
            error: function(e) {
                console.log("What happened", e);
            }
        })
      }, []);
    
    return <></>
}