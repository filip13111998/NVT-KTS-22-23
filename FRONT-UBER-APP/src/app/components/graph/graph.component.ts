import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ReportRideInterface } from 'src/app/model/ReportRideInterface';
import { GraphService } from 'src/app/services/graph.service';
import * as d3 from 'd3';

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.css']
})
export class GraphComponent {
  number : ReportRideInterface = {
    "averageData" : 0.0,
    "sumData": 0.0,
    "dayDTOList" : []
  };
  meters : ReportRideInterface = {
    "averageData" : 0.0,
    "sumData": 0.0,
    "dayDTOList" : []
  };
  price : ReportRideInterface = {
    "averageData" : 0.0,
    "sumData": 0.0,
    "dayDTOList" : []
  };

  reportForm = new FormGroup({
    start: new FormControl(''),
    end: new FormControl('')
  })

  condition:boolean = false;

  constructor(private graphService : GraphService) {
  }

  ngOnInit(): void {

  }


  createPlot(param: ReportRideInterface , id:string , description:string): void {

    const margin = { top: 20, right: 20, bottom: 30, left: 50 };
    const width = 600 - margin.left - margin.right;
    const height = 400 - margin.top - margin.bottom;

    // Create an SVG element within the container
    const svg = d3
      .select('#'+id)
      .append('svg')
      .attr('width', width + margin.left + margin.right)
      .attr('height', height + margin.top + margin.bottom)
      .append('g')
      .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    // Define the scales for the x-axis and y-axis
    const xScale = d3
      .scaleTime()
      .domain(d3.extent(param.dayDTOList, (d: { date: any; }) => d.date))
      .range([0, width]);

    const yScale = d3
      .scaleLinear()
      .domain([0, d3.max(param.dayDTOList, (d: { data: any; }) => d.data)])
      .range([height, 0]);

    // Define the line functions for kilometers, price, and rideNumber
    const lineData = d3
      .line()
      .x((d: { date: any; }) => xScale(d.date))
      .y((d: { data: any; }) => yScale(d.data));

    // Add the line paths to the SVG
    svg
      .append('path')
      .datum(param.dayDTOList)
      .attr('class', 'line')
      .attr('d', lineData);

    // Add data points to the SVG
    svg
      .selectAll('.data-point')
      .data(param.dayDTOList)
      .enter()
      .append('circle')
      .attr('class', 'data-point')
      .attr('cx', (d: any) => xScale(d.date))
      .attr('cy', (d: any) => yScale(d.data))
      .attr('r', 4)
      .style('fill', 'steelblue');

    // Add x-axis to the SVG
    svg
      .append('g')
      .attr('class', 'x-axis')
      .attr('transform', 'translate(0,' + height + ')')
      .call(d3.axisBottom(xScale));

    // Add y-axis to the SVG
    svg
      .append('g')
      .attr('class', 'y-axis')
      .call(d3.axisLeft(yScale));

    // Add labels to the x-axis and y-axis
    svg
      .append('text')
      .attr('transform', 'translate(' + (width / 2) + ' ,' + (height + margin.top + 20) + ')')
      .style('text-anchor', 'middle')
      .text('Date');

    svg
      .append('text')
      .attr('transform', 'rotate(-90)')
      .attr('y', 0 - margin.left)
      .attr('x', 0 - (height / 2))
      .attr('dy', '1em')
      .style('text-anchor', 'middle')
      .text(description);
  }


  public rideReport(): void {

    let start:string = this.reportForm.value.start!;
    let end:string = this.reportForm.value.end!;

    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let role = parsedToken['roles'];
      let usrn = parsedToken['sub'];
      console.log('role: ' + role);
      console.log('sub: ' + usrn);
      this.rideNumber(start , end , usrn , role);
      this.rideMeters(start , end , usrn , role);
      this.ridePrice(start , end , usrn , role);

      // this.createPlot();
    }
  }

  public rideNumber(start:string , end:string , username:string , role:string): void {
    this.graphService.rideNumber(start,end , username , role).subscribe((data)=>{
      console.log('rideNumber pre pucanja');
      console.log(data);
      if (data === null || data.dayDTOList.length === 0){
        console.log('dosaoo')
        this.condition = true;
        return;
      }
      this.condition = false;
      this.number = data;
      this.createPlot(data , "numberContainer" , "Ride NUMBER");
    });
  }

  public rideMeters(start:string , end:string , username:string , role:string): void {
    this.graphService.rideMeters(start,end , username , role).subscribe((data)=>{
      console.log('rideMeters pre pucanja');
      console.log(data);
      if (data === null || data.dayDTOList.length === 0){
        console.log('dosaoo2')
        this.condition = true;
        return;
      }
      this.condition = false;
      this.meters = data;
      this.createPlot(data, "metersContainer","Ride METERS");
    });
  }

  public ridePrice(start:string , end:string , username:string , role:string): void {
    this.graphService.ridePrice(start,end , username , role).subscribe((data)=>{
      console.log('ridePrice pre pucanja');
      console.log(data);
      if (data === null || data.dayDTOList.length === 0){
        console.log('dosaoo3')
        this.condition = true;
        return;
      }
      this.condition = false;
      this.price = data;
      this.createPlot(data ,"priceContainer","Ride PRICE");
    });
  }
}
