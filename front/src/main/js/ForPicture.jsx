import React from 'react';
import {Dropdown} from "primereact/components/dropdown/Dropdown";






const Question = () => (
  <div>
    <span>What to choose?</span>
    <Dropdown
      value={"Javascript"}
      onChange={() =>
        console.log("You are traitor!!!")
      }
      options={[
        {
          label: "Javascript",
          value: "js"
        },
        {
          label: "Kotlin",
          value: "kt"
        }
      ]}
    />
  </div>
);


Question.hasOwnProperty();
