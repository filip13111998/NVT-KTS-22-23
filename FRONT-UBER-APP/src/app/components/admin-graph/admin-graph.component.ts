import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ReportRideInterface } from 'src/app/model/ReportRideInterface';
import { GraphService } from 'src/app/services/graph.service';
import * as d3 from 'd3';

@Component({
  selector: 'app-admin-graph',
  templateUrl: './admin-graph.component.html',
  styleUrls: ['./admin-graph.component.css']
})
export class AdminGraphComponent {
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


  createPlot(): void {
    console.log(this.number);
    console.log(this.price);
    console.log(this.meters);
    const margin = { top: 20, right: 20, bottom: 30, left: 50 };
    const width = 600 - margin.left - margin.right;
    const height = 400 - margin.top - margin.bottom;

    // Create an SVG element within the container
    const svg = d3
      .select('#plotContainer')
      .append('svg')
      .attr('width', width + margin.left + margin.right)
      .attr('height', height + margin.top + margin.bottom)
      .append('g')
      .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');


    // Define the scales for the x-axis and y-axis
    const xScale = d3
      .scaleTime()
      .domain(d3.extent(this.price.dayDTOList, (d: { date: any; }) => d.date))
      .range([0, width]);

    const yScale = d3
      .scaleLinear()
      .domain([0, d3.max(this.price.dayDTOList, (d: { data: any; }) => d.data)])
      .range([height, 0]);

      // Define the line functions for kilometers, price, and rideNumber
    const lineKilometers = d3
      .line()
      .x((d: { date: any; }) => xScale(d.date))
      .y((d: { data: any; }) => yScale(d.data));

    const linePrice = d3
      .line()
      .x((d: { date: any; }) => xScale(d.date))
      .y((d: { data: any; }) => yScale(d.data));

    const lineRideNumber = d3
      .line()
      .x((d: { date: any; }) => xScale(d.date))
      .y((d: { data: any; }) => yScale(d.data));

    svg
      .append('path')
      .datum(this.meters.dayDTOList)
      .attr('class', 'line')
      .attr('d', lineKilometers);

    svg
      .append('path')
      .datum(this.price.dayDTOList)
      .attr('class', 'line')
      .attr('d', linePrice);

    svg
      .append('path')
      .datum(this.number.dayDTOList)
      .attr('class', 'line')
      .attr('d', lineRideNumber);

    svg
      .selectAll('.data-point')
      .data(this.meters.dayDTOList)
      .enter()
      .append('circle')
      .attr('class', 'data-point')
      .attr('cx', (d: any) => xScale(d.date))
      .attr('cy', (d: any) => yScale(d.data))
      .attr('r', 4)
      .style('fill', 'steelblue');

    svg
      .selectAll('.data-point-price')
      .data(this.price.dayDTOList)
      .enter()
      .append('circle')
      .attr('class', 'data-point-price')
      .attr('cx', (d: any) => xScale(d.date))
      .attr('cy', (d: any) => yScale(d.data))
      .attr('r', 4)
      .style('fill', 'orange');

    svg
      .selectAll('.data-point-ride')
      .data(this.number.dayDTOList)
      .enter()
      .append('circle')
      .attr('class', 'data-point-ride')
      .attr('cx', (d: any) => xScale(d.date))
      .attr('cy', (d: any) => yScale(d.data))
      .attr('r', 4)
      .style('fill', 'green');

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
      .text('Kilometers / Price / Ride Number');
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
      // this.rideMeters(start , end , usrn , role);
      // this.ridePrice(start , end , usrn , role);

      // this.createPlot();
    }
  }

  public rideNumber(start:string , end:string , username:string , role:string): void {
    this.graphService.rideNumber(start,end , username , role).subscribe((data)=>{

      if (data === null || data.dayDTOList.length === 0){

        this.condition = true;
        return;
      }
      // this.createSVG();
      this.condition = false;
      this.number = data;

      this.graphService.rideMeters(start,end , username , role).subscribe((data)=>{

        if (data === null || data.dayDTOList.length === 0){
          this.condition = true;
          return;
        }
        this.condition = false;
        this.meters = data;

        this.graphService.ridePrice(start,end , username , role).subscribe((data)=>{

          if (data === null || data.dayDTOList.length === 0){
            this.condition = true;
            return;
          }
          this.condition = false;
          this.price = data;
          console.log(this.number);
          this.createPlot();
        });
        // this.createPlot(data);
      });
      // this.createPlot(data);
    });
  }


}
